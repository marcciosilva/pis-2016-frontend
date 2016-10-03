package com.sonda.emsysmobile.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.core.EventDto;
import com.sonda.emsysmobile.model.core.ExtensionDto;
import com.sonda.emsysmobile.model.responses.EventsResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.ApiCallback;
import com.sonda.emsysmobile.network.RequestFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssainz on 10/1/16.
 */
public class EventManager {

    private static EventManager mInstance;
    public Context mContext;

    private List<EventDto> mEvents;
    private List<ExtensionDto> mExtensions;

    EventManager(Context context) {
        mContext = context;
        mEvents = new ArrayList<>();
        mExtensions = new ArrayList<>();
    }

    /**
     * Event Manager singleton to manage events and extensions objects
     * @param context Must be the Application Context to make requests with the correct context
     * @return An EventManager instance
     */
    public static synchronized EventManager getIntance(Context context) {
        if (mInstance == null) {
            mInstance = new EventManager(context);
        }
        return mInstance;
    }

    public void fetchEvents(final ApiCallback<List<ExtensionDto>> callback) {
        Request eventsRequest = RequestFactory.eventsRequest(mContext, new Response.Listener<EventsResponse>() {
            @Override
            public void onResponse(EventsResponse response) {
                int responseCode = response.getCode();
                switch (responseCode) {
                    case 0:
                        setEvents(response.getEvents());
                        callback.onSuccess(mExtensions);
                        break;
                    default:
                        callback.onError(mContext.getString(R.string.error_generic));
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Tag", error.toString());
                callback.onError(mContext.getString(R.string.error_generic));
            }
        });
        AppRequestQueue.getInstance(mContext).addToRequestQueue(eventsRequest);
    }

    private void setEvents(List<EventDto> events) {
        mEvents = events;
        mExtensions.clear();
        for (EventDto event: events) {
            List<ExtensionDto> eventExtensions = event.getExtensions();
            for (ExtensionDto extension : eventExtensions) {
                extension.setEvent(event);
            }
            mExtensions.addAll(eventExtensions);
        }
    }
}
