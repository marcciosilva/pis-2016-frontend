package com.sonda.emsysmobile.events.managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseArray;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.EventDetailsResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.EventsResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.EventDetailsRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.EventsRequest;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.notifications.NotificationsEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ssainz on 10/1/16.
 */
public final class EventManager {

    private static final String NOTIFICATION_KEY = "notification";
    private static final String EVENTS_UPDATED = "events_updated";

    private static EventManager mInstance;
    private Context mContext;
    private List<EventDto> mEvents;

    /**
     * Using SparseArray because it is intended to be more memory efficient than using a HashMap
     * to map Integers to Objects.
     * Visit this link to know more about use of SparseArray in Android: https://developer
     * .android.com/reference/android/util/SparseArray.html
     */
    private SparseArray<ExtensionDto> mExtensions;

    private static final String TAG = EventManager.class.getName();

    private EventManager(Context context) {
        mContext = context;
        mEvents = new ArrayList<>();
        mExtensions = new SparseArray<>();
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(broadcastReceiverEvents,
                        new IntentFilter(NotificationsEvents.UPDATE_EVENTS_LIST.toString()));
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(broadcastReceiverEvents,
                        new IntentFilter(NotificationsEvents.UPDATE_ONE_EVENT.toString()));
    }

    public final void onLogout() {
        mEvents.clear();
        mExtensions.clear();
    }

    /**
     * Singleton para manejar objetos de eventos y extensiones.
     *
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
        if (mExtensions.size() == 0) {
            EventsRequest<EventsResponse> request =
                    new EventsRequest<>(mContext, EventsResponse.class);
            request.setListener(new Response.Listener<EventsResponse>() {
                @Override
                public void onResponse(EventsResponse response) {
                    int responseCode = response.getCode();
                    if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                        setEvents(response.getEvents());
                        callback.onSuccess(getExtensionsList());
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
        } else {
            callback.onSuccess(getExtensionsList());
        }
    }

    public final void fetchEvents(final ApiCallback<List<EventDto>> callback) {
        if (mEvents.size() == 0) {
            updateEvents(new Response.Listener<EventsResponse>() {
                @Override
                public void onResponse(EventsResponse response) {
                    int responseCode = response.getCode();
                    if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
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
                    Intent intent = new Intent(EVENTS_UPDATED);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
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

    public final void getEventDetail(int eventId, final ApiCallback<EventDto> callback) {
        EventDetailsRequest<EventDetailsResponse> request =
                new EventDetailsRequest<>(mContext, EventDetailsResponse.class);
        request.setAttributes(eventId);
        request.setListener(new Response.Listener<EventDetailsResponse>() {
            @Override
            public void onResponse(EventDetailsResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    EventDto event = response.getEvent();
                    if (event != null) {
                        for (ExtensionDto extension : event.getExtensions()) {
                            extension.setEvent(event);
                        }
                    }
                    callback.onSuccess(event);
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

    private void setEvents(List<EventDto> events) {
        mEvents = events;
        mExtensions.clear();
        for (EventDto event : mEvents) {
            List<ExtensionDto> eventExtensions = event.getExtensions();
            for (ExtensionDto extension : eventExtensions) {
                extension.setEvent(event);
                mExtensions.append(extension.getIdentifier(), extension);
            }
        }
    }

    public void setEventAsRead(EventDto event) {
        List<ExtensionDto> eventExtensions = event.getExtensions();
        for (ExtensionDto extension : eventExtensions) {
            int extensionID = extension.getIdentifier();
            if (mExtensions.get(extensionID) != null) {
                mExtensions.get(extensionID).setModified(false);
            }
        }
        Intent intent = new Intent(EVENTS_UPDATED);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private List<ExtensionDto> getExtensionsList() {
        if (mExtensions == null) {
            return null;
        }
        ArrayList<ExtensionDto> arrayList = new ArrayList<>(mExtensions.size());
        for (int i = 0; i < mExtensions.size(); i++) {
            arrayList.add(mExtensions.valueAt(i));
        }
        sortExtensionsByPriority(arrayList);
        return arrayList;
    }

    private void sortExtensionsByPriority(List<ExtensionDto> extensions) {
        Collections.sort(extensions, new Comparator<ExtensionDto>() {
            public int compare(ExtensionDto ext1, ExtensionDto ext2) {
                return ext1.getPriority().compareTo(ext2.getPriority());
            }
        });
    }

    /**
     * Catches notification events posted by MyFirebaseMessagingService
     */
    private BroadcastReceiver broadcastReceiverEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                Notification notification = (Notification) intent.getExtras().get(NOTIFICATION_KEY);
                if (notification != null) {
                    Log.i(TAG, "Receiving notificación con código: " + notification.getCode());
                    if (intent.getAction()
                            .equals(NotificationsEvents.UPDATE_EVENTS_LIST.toString())) {
                        updateEvents(null, null);
                    } else if (intent.getAction()
                            .equals(NotificationsEvents.UPDATE_ONE_EVENT.toString())) {
                        ExtensionDto extensionDto = mExtensions.get(notification.getObjectId());
                        //TODO: Update just one event with an API Call
                        if (extensionDto != null) {
                            extensionDto.setModified(true);
                            Intent eventsIntent = new Intent(EVENTS_UPDATED);
                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(eventsIntent);
                        }
                    }
                }
            }
        }
    };
}
