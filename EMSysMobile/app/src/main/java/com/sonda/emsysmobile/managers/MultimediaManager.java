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
