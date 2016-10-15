package com.sonda.emsysmobile.events.managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.sonda.emsysmobile.backendcommunication.model.responses.ResponseCodeCategory;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.backendcommunication.model.responses.EventsResponse;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.services.request.EventsRequest;
import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.notifications.NotificationsEvents;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by ssainz on 10/1/16.
 */
public class EventManager {

    private static final String NOTIFICATION_KEY = "notification";

    private static EventManager mInstance;
    private Context mContext;

    private List<EventDto> mEvents;
    private List<ExtensionDto> mExtensions;

    private static final String TAG = EventManager.class.getName();

    EventManager(Context context) {
        mContext = context;
        mEvents = new ArrayList<>();
        mExtensions = new ArrayList<>();
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(broadcastReceiverEvents, new IntentFilter(NotificationsEvents.UPDATE_EVENTS_LIST.toString()));
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(broadcastReceiverEvents, new IntentFilter(NotificationsEvents.UPDATE_ONE_EVENT.toString()));
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

    public final void fetchExtensions(final ApiCallback<List<ExtensionDto>> callback) {
        EventsRequest<EventsResponse> request = new EventsRequest<>(mContext, EventsResponse.class);
        request.setListener(new Response.Listener<EventsResponse>() {
            @Override
            public void onResponse(EventsResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ResponseCodeCategory.SUCCESS.getNumVal()) {
                    setEvents(response.getEvents());
                    callback.onSuccess(mExtensions);
                } else {
                    //TODO soportar mensaje de error en EventsResponse
                    //callback.onError(response.getInnerResponse().getMsg(), responseCode);
                    callback.onLogicError("Unsupported", 1);
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onNetworkError(error);
            }
        });
        request.execute();
    }

    public final void fetchEvents(final ApiCallback<List<EventDto>> callback) {
        if (mEvents.size() == 0) {
            updateEvents(new Response.Listener<EventsResponse>() {
                @Override
                public void onResponse(EventsResponse response) {
                    int responseCode = response.getCode();
                    if (responseCode == ResponseCodeCategory.SUCCESS.getNumVal()) {
                        setEvents(response.getEvents());
                        callback.onSuccess(mEvents);
                    } else {
                        //TODO soportar mensaje de error en EventsResponse
                        callback.onLogicError("Unsupported", 1);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onNetworkError(error);
                }
            });
        } else {
            callback.onSuccess(mEvents);
        }
    }

    private void updateEvents(Response.Listener<EventsResponse> responseListener,
                              Response.ErrorListener errorListener) {
        EventsRequest<EventsResponse> request = new EventsRequest<>(mContext, EventsResponse.class);
        if (responseListener == null) {
            responseListener = new Response.Listener<EventsResponse>() {
                @Override
                public void onResponse(EventsResponse response) {
                    setEvents(response.getEvents());
                }
            };
        }
        if (errorListener == null) {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: Manage error when updating events
                }
            };
        }
        request.setListener(responseListener);
        request.setErrorListener(errorListener);
        request.execute();
    }

    private void setEvents(List<EventDto> events) {
        mEvents = events;
        mExtensions.clear();
        for (EventDto event: mEvents) {
            List<ExtensionDto> eventExtensions = event.getExtensions();
            for (ExtensionDto extension : eventExtensions) {
                extension.setEvent(event);
            }
            mExtensions.addAll(eventExtensions);
        }
        sortExtensionsByPriority();
    }

    private void sortExtensionsByPriority() {
        Collections.sort(mExtensions, new Comparator<ExtensionDto>() {
            public int compare(ExtensionDto ext1, ExtensionDto ext2) {
                return ext1.getPriority().compareTo(ext2.getPriority());
            }
        });
    }

    /**
     * Receives notifications
     */
    private BroadcastReceiver broadcastReceiverEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                Notification notification = (Notification) intent.getExtras().get(NOTIFICATION_KEY);
                if (notification != null) {
                    Log.i(TAG, "Receiving notificación con código: " + notification.getCode());
                    if (intent.getAction().equals(NotificationsEvents.UPDATE_EVENTS_LIST.toString())) {
                        updateEvents(null, null);
                    }
                }
            }
        }
    };
}