package com.sonda.emsysmobile.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.core.EventDto;
import com.sonda.emsysmobile.model.core.ExtensionDto;
import com.sonda.emsysmobile.model.responses.EventsResponse;
import com.sonda.emsysmobile.network.ApiCallback;
import com.sonda.emsysmobile.network.services.request.EventsRequest;

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

    private static final String TAG = EventManager.class.getName();

    EventManager(Context context) {
        mContext = context;
        mEvents = new ArrayList<>();
        mExtensions = new ArrayList<>();
    }

    /**
     * Singleton para manejar objetos de eventos y extensiones.
     * @param context Debe ser el contexto de la aplicacion para realizar requests con el contexto
     *                correcto.
     * @return Una instancia de EventManager.
     */
    public static synchronized EventManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EventManager(context);
        }
        return mInstance;
    }

    public void fetchEvents(final ApiCallback<List<ExtensionDto>> callback) {
        EventsRequest<EventsResponse> request = new EventsRequest<>(mContext, EventsResponse.class);
        request.setListener(new Response.Listener<EventsResponse>() {
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
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
                callback.onError(mContext.getString(R.string.error_generic));
            }
        });
        request.execute();
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
