package com.sonda.emsysmobile.managers;

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
    private static final String TAG = EventManager.class.getName();
    private static EventManager mInstance;
    private Context mContext;
    private List<EventDto> mEvents;
    /**
     * Using SparseArray because it is intended to be more memory efficient than using a HashMap
     * to map Integers to Objects.
     * Visit this link to know more about use of SparseArray in Android:
     * https://developer.android.com/reference/android/util/SparseArray.html
     */
    private SparseArray<ExtensionDto> mExtensions;
    /**
     * Catches notification events posted by MyFirebaseMessagingService
     */
    private BroadcastReceiver broadcastReceiverEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                Notification notification = (Notification) intent.getExtras().get(NOTIFICATION_KEY);
                if (notification != null) {
                    Log.i(TAG, "Receiving notification with code: " + notification.getCode());
                    if (intent.getAction()
                            .equals(NotificationsEvents.UPDATE_EVENTS_LIST.toString())) {
                        updateEvents(null, null);
                    } else if (intent.getAction()
                            .equals(NotificationsEvents.UPDATE_ONE_EVENT.toString())) {
                        ExtensionDto extensionDto = mExtensions.get(notification.getEventId());
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

    public void onLogout() {
        mEvents.clear();
        mExtensions.clear();
    }

    public void fetchExtensions(boolean fromService, final String filter, final boolean onMap,
                                final ApiCallback<List<ExtensionDto>> callback) {
        if (mExtensions.size() == 0 || fromService) {
            EventsRequest<EventsResponse> request =
                    new EventsRequest<>(mContext, EventsResponse.class);
            request.setListener(new Response.Listener<EventsResponse>() {
                @Override
                public void onResponse(EventsResponse response) {
                    int responseCode = response.getCode();
                    if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                        setEvents(response.getEvents());
                        callback.onSuccess(getExtensionsList(filter, onMap));
                    } else {
                        //TODO soportar mensaje de error en EventsResponse
                        //callback.onError(response.getInnerResponse().getMsg(), responseCode);
                        callback.onLogicError(null, responseCode);
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
            callback.onSuccess(getExtensionsList(filter, onMap));
        }
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

    private List<ExtensionDto> getExtensionsList(String filter, boolean onMap) {
        if (mExtensions == null) {
            return null;
        }
        ArrayList<ExtensionDto> arrayList = new ArrayList<>(mExtensions.size());
        ExtensionDto extension;
        if (onMap) {
            for (int i = 0; i < mExtensions.size(); i++) {
                extension = mExtensions.valueAt(i);
                if ((extension.isAssigned()) && (extension.getEvent().getLatitude() == 0.0) &&
                        (extension.getEvent().getLongitude() == 0.0)) {
                    arrayList.add(extension);
                }
            }
        } else {
            for (int i = 0; i < mExtensions.size(); i++) {
                extension = mExtensions.valueAt(i);
                if (extension.isAssigned()) {
                    arrayList.add(extension);
                }
            }
        }

        switch (filter) {
            case "Prioridad":
                sortExtensionsByPriority(arrayList);
                break;
            case "Fecha":
                sortExtensionsByDate(arrayList);
                break;
            case "Zona":
                sortExtensionsByZone(arrayList);
                break;
        }
        return arrayList;
    }

    private void sortExtensionsByPriority(List<ExtensionDto> extensions) {
        Collections.sort(extensions, new Comparator<ExtensionDto>() {
            public int compare(ExtensionDto ext1, ExtensionDto ext2) {
                return ext1.getPriority().compareTo(ext2.getPriority());
            }
        });
    }

    private void sortExtensionsByDate(List<ExtensionDto> extensions) {
        Collections.sort(extensions, new Comparator<ExtensionDto>() {
            public int compare(ExtensionDto ext1, ExtensionDto ext2) {
                return ext2.getTimeStamp().compareTo(ext1.getTimeStamp());
            }
        });
    }

    private void sortExtensionsByZone(List<ExtensionDto> extensions) {
        Collections.sort(extensions, new Comparator<ExtensionDto>() {
            public int compare(ExtensionDto ext1, ExtensionDto ext2) {
                return ext1.getZone().getName().compareTo(ext2.getZone().getName());
            }
        });
    }

    public void fetchEvents(final ApiCallback<List<EventDto>> callback) {
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
                        callback.onLogicError(null, responseCode);
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
        Response.Listener<EventsResponse> localResponseListener = responseListener;
        Response.ErrorListener localErrorListener = errorListener;
        if (localResponseListener == null) {
            localResponseListener = new Response.Listener<EventsResponse>() {
                @Override
                public void onResponse(EventsResponse response) {
                    setEvents(response.getEvents());
                    Intent intent = new Intent(EVENTS_UPDATED);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            };
        }
        if (localErrorListener == null) {
            localErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: Manage error when updating events
                }
            };
        }
        request.setListener(localResponseListener);
        request.setErrorListener(localErrorListener);
        request.execute();
    }

    public EventDto getLocalEventDetail(int eventId) {
        for (EventDto event : mEvents) {
            if (event.getIdentifier() == eventId) {
                return event;
            }
        }
        return null;
    }

    public void getEventDetail(int eventId, final ApiCallback<EventDto> callback) {
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
                        updateEvent(event);
                        for (ExtensionDto extension : event.getExtensions()) {
                            extension.setEvent(event);
                            mExtensions.put(extension.getIdentifier(), extension);
                        }
                    }
                    callback.onSuccess(event);
                } else {
                    //TODO soportar mensaje de error en EventsResponse
                    //callback.onError(response.getInnerResponse().getMsg(), responseCode);
                    callback.onLogicError(null, responseCode);
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

    public void updateEvent(EventDto event) {
        for (int index = 0; index < mEvents.size(); index++) {
            if (mEvents.get(index).getIdentifier() == event.getIdentifier()) {
                mEvents.remove(index);
            }
        }
        mEvents.add(event);
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
}
