package com.sonda.emsysmobile.ui.eventdetail.multimedia;

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
public final class MultimediaManager {

    private static MultimediaManager mInstance;
    private Context mContext;

    private static final String TAG = MultimediaManager.class.getName();

    private MultimediaManager(Context context) {
        mContext = context;
    }

    public final void onLogout() {
    }

    /**
     * Singleton para manejar objetos multimedia.
     *
     * @param context Debe ser el contexto de la aplicacion para realizar requests con el contexto
     *                correcto.
     * @return Una instancia de MultimediaManager.
     */
    public static synchronized MultimediaManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MultimediaManager(context);
        }
        return mInstance;
    }

    public final void fetchImages(final ApiCallback<List<ExtensionDto>> callback) {



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

}
