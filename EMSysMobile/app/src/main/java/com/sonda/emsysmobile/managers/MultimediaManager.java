package com.sonda.emsysmobile.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.services.request.AttachImageRequest;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.CustomOkHttpClient;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by marccio on 10/29/16.
 */
public final class MultimediaManager {

    private static MultimediaManager mInstance;
    private Context mContext;

    private MultimediaManager(Context context) {
        mContext = context;

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        final String authToken = sharedPrefs.getString("access_token", "");
        OkHttpClient multimediaClient;
        if (BuildConfig.BASE_URL.contains("https")) {
            multimediaClient = CustomOkHttpClient.getCustomOkHttpClient(context);
        } else {
            multimediaClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("auth", authToken)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();
        }

        Picasso picasso = new Picasso.Builder(context)
                 .downloader(new OkHttp3Downloader(multimediaClient))
                 .build();
        Picasso.setSingletonInstance(picasso);
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
        final int quality = 60;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
