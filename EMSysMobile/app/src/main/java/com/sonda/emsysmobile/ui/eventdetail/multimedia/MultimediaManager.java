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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by marccio on 10/29/16.
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
        if ((mInstance == null) || (!context.equals(mInstance.mContext))) {
            mInstance = new MultimediaManager(context);
        }
        return mInstance;
    }

    public final void setImageDescriptions(List<ImageDescriptionDto> imageDescriptions) {
        // Si me llegan las mismas descripciones no se altera nada.
        if (!mImageDescriptions.equals(imageDescriptions)) {
            mImageDescriptions = imageDescriptions;
            mImageDataList.clear();
        }
    }

    public final void clearInternalStorage() {
        File dir = mContext.getFilesDir();
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                Log.d(TAG, "Borrando archivo " + children[i]);
                new File(dir, children[i]).delete();
            }
        }
    }

    public final void fetchImages(final ApiCallback<List<ImageDataDto>> callback) {
        if (mImageDataList.isEmpty()) {
            // Genero array de strings con los nombres de los archivos del directorio
            // de almacenamiento.
            File[] files = mContext.getFilesDir().listFiles();
            List<String> fileNames = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                fileNames.add(files[i].getName());
            }
            // Hago la request en caso de ser necesario.
            int avoidedRequests = 0;
            for (ImageDescriptionDto imageDescription : mImageDescriptions) {
                Log.d(TAG, "avoidedRequests = " + Integer.toString(avoidedRequests) +
                        ", mImageDataList.size() = " +
                        Integer.toString(mImageDataList.size()));
                boolean shouldRequest = true;
                Log.d(TAG, "Chequeando " + Integer.toString(imageDescription.getId()) + ".");
                for (String fileName : fileNames) {
                    // Si algun archivo empieza con el nombre de la descripcion y punto,
                    // no se lo pide nuevamente.
                    if (fileName.startsWith(imageDescription.getId() + ".")) {
                        shouldRequest = false;
                        avoidedRequests++;
                        Log.d(TAG, "No se hace request para archivo " + fileName);
                        break;
                    }
                }
                // Variable a ser utilizada dentro de listener (debe ser final).
                if (shouldRequest) {
                    final int auxAvoidedRequests = avoidedRequests;
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
                                if (mImageDataList.size() + auxAvoidedRequests ==
                                        mImageDescriptions.size()) {
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
                } else {
                    Log.d(TAG, "viejita");
                    if (mImageDataList.size() + avoidedRequests ==
                            mImageDescriptions.size()) {
                        callback.onSuccess(mImageDataList);
                    }
                }
            }
        } else {
            // Se asume que si no es vacio es porque esta cacheado de un pedido anterior.
            callback.onSuccess(mImageDataList);
        }
    }

}
