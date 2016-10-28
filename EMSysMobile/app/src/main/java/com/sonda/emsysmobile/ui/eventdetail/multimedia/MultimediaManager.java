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
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.EventDetailsResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.EventsResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.GetImageDataResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.EventDetailsRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.EventsRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.GetImageDataRequest;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
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
    private List<ImageDescriptionDto> mImageDescriptions;
    private List<ImageDataDto> mImageDataList;
    private Context mContext;

    private static final String TAG = MultimediaManager.class.getName();

    private MultimediaManager(Context context) {
        mContext = context;
        mImageDescriptions = new ArrayList<>();
        mImageDataList = new ArrayList<>();
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

    public final void setImageDescriptions(List<ImageDescriptionDto> imageDescriptions) {
        // Si me llegan las mismas descripciones no se altera nada.
        if (!mImageDescriptions.equals(imageDescriptions)) {
            Log.d(TAG, "Clearing image descriptions...");
            mImageDescriptions.clear();
            mImageDescriptions = imageDescriptions;
            mImageDataList.clear();
        }
    }

    public final void fetchImages(final ApiCallback<List<ImageDataDto>> callback) {
        if (mImageDataList.isEmpty()) {
            for (ImageDescriptionDto imageDescription : mImageDescriptions) {
                GetImageDataRequest<GetImageDataResponse> request =
                        new GetImageDataRequest<>(mContext, GetImageDataResponse.class);
                request.setAttributes(imageDescription.getId());
                request.setListener(new Response.Listener<GetImageDataResponse>() {
                    @Override
                    public void onResponse(GetImageDataResponse response) {
                        int responseCode = response.getCode();
                        if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                            mImageDataList.add(response.getImageData());
                            // Si tienen el mismo size es porque me llegaron todas las imagenes.
                            if (mImageDataList.size() == mImageDescriptions.size()) {
                                callback.onSuccess(mImageDataList);
                            }
                        } else {
                            // TODO asociar un mensaje de error para cada codigo posible.
                            callback.onLogicError("Unsupported", responseCode);
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
        } else {
            // Se asume que si no es vacio es porque esta cacheado de un pedido anterior.
            callback.onSuccess(mImageDataList);
        }
    }

}
