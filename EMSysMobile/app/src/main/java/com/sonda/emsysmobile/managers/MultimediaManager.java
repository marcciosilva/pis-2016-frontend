package com.sonda.emsysmobile.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.AppRequestQueue;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.GetImageDataResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.AttachImageRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.GetImageDataRequest;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.attr.bitmap;

/**
 * Created by marccio on 10/29/16.
 */
public final class MultimediaManager {

    private static MultimediaManager mInstance;
    private List<ImageDescriptionDto> mImageDescriptions;
    private List<ImageDataDto> mImageDataList;
    private Context mContext;
    private Map<ImageDescriptionDto, Boolean> mImagesAlreadyInPath;

    private static final String TAG = MultimediaManager.class.getName();

    private MultimediaManager(Context context) {
        mContext = context;
        mImageDescriptions = new ArrayList<>();
        mImageDataList = new ArrayList<>();
        mImagesAlreadyInPath = new HashMap<>();
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
        Log.d(TAG, "Llamado a fetchImages");
        mImageDataList.clear();
        mImagesAlreadyInPath.clear();
        // Genero array de strings con los nombres de los archivos del directorio
        // de almacenamiento.
        File[] files = mContext.getFilesDir().listFiles();
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            fileNames.add(files[i].getName());
        }
        // Genero un map que para cada descripcion indica si tengo que hacer la request
        // o no.
        for (ImageDescriptionDto imageDescription : mImageDescriptions) {
            boolean shouldRequest = true;
            Log.d(TAG, "Chequeando " + Integer.toString(imageDescription.getId()) + ".");
            for (String fileName : fileNames) {
                // Si algun archivo empieza con el nombre de la descripcion y punto,
                // no se lo pide nuevamente.
                if (fileName.startsWith(imageDescription.getId() + ".")) {
                    shouldRequest = false;
                    Log.d(TAG, "No se hace request para archivo " + fileName);
                    break;
                }
            }
            mImagesAlreadyInPath.put(imageDescription, shouldRequest);
        }
        // Cantidad de requests a realizar, para saber cuando llamar al callback.
        final int requestsToBeMade = Collections
                .frequency(new ArrayList<Boolean>(mImagesAlreadyInPath.values()), true);
        // Hago requests necesarias.
        if (requestsToBeMade != 0) {
            for (final ImageDescriptionDto imageDescription : mImageDescriptions) {
                // Variable a ser utilizada dentro de listener (debe ser final).
                if (mImagesAlreadyInPath.get(imageDescription)) {
                    GetImageDataRequest<GetImageDataResponse> request =
                            new GetImageDataRequest<>(mContext, GetImageDataResponse.class);
                    request.setAttributes(imageDescription.getId());
                    request.setListener(new Response.Listener<GetImageDataResponse>() {
                        @Override
                        public void onResponse(GetImageDataResponse response) {
                            int responseCode = response.getCode();
                            if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                                mImageDataList.add(response.getImageData());
                                // Si tienen el mismo size es porque me llegaron todas las
                                // imagenes.
                                if (mImageDataList.size() == requestsToBeMade) {
                                    callback.onSuccess(mImageDataList);
                                }
                            } else {
                                // TODO asociar un mensaje de error para cada codigo posible.
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
            }
        } else {
            // Si no hay ninguna request que hacer, se devuelve una estructura vacia.
            callback.onSuccess(mImageDataList);
        }
    }

    public final void uploadImage(int extensionId, String name, Bitmap image, final ApiCallback callback) {
        AttachImageRequest<EmsysResponse> request = new AttachImageRequest<>(mContext, EmsysResponse.class);
        request.setAttributes(extensionId, name, getBase64Image(image));
        request.setListener(new Response.Listener<EmsysResponse>() {
            @Override
            public void onResponse(EmsysResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    callback.onSuccess(response);
                } else {
                    // TODO asociar un mensaje de error para cada codigo posible.
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

    public String getBase64Image(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
