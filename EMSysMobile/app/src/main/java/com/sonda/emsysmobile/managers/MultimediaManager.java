package com.sonda.emsysmobile.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.services.request.AttachImageRequest;

import java.io.ByteArrayOutputStream;

/**
 * Created by marccio on 10/29/16.
 */
public final class MultimediaManager {

    private static MultimediaManager mInstance;
    private Context mContext;

    private MultimediaManager(Context context) {
        mContext = context;
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

    public void uploadImage(int extensionId, String name, Bitmap image,
                            final ApiCallback callback) {
        AttachImageRequest<EmsysResponse> request =
                new AttachImageRequest<>(mContext, EmsysResponse.class);
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
        final int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
