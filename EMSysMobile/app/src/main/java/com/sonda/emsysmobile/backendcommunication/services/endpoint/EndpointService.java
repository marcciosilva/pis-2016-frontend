package com.sonda.emsysmobile.backendcommunication.services.endpoint;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.backendcommunication.AppRequestQueue;
import com.sonda.emsysmobile.backendcommunication.GsonGetRequest;
import com.sonda.emsysmobile.backendcommunication.GsonPostRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.AbstractRequest;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by mserralta on 29/9/16.
 */
public class EndpointService<T> {

    private static final String TAG = EndpointService.class.getName();
    //setting timeout to 60 sec beacuse azure delay is too long.
    private static final int MY_SOCKET_TIMEOUT_MS = 60000;
    private Context context;

    public EndpointService(Context context) {
        this.context = context;
    }

    public final void execute(String url, AbstractRequest.RequestType requestType, String path,
                              JsonObject jsonObject, Type type, Response.Listener listener,
                              Response.ErrorListener errorListener) {
        Log.d(TAG, "Base URL: " + url);
        String fullUrl = url + path;
        if (jsonObject != null) {
            // Se imprime body en json a enviar y la IP a la que va dirigida la request.
            Log.d(TAG, jsonObject.toString());
        }
        Log.d("IP_REQUEST", fullUrl);
        // Se obtiene el token de autenticacion.
        String accessToken = getAccesToken();
        Request<T> request = null;
        // Estructura encargada de llevar los headers de la request.
        final Map<String, String> mHeaders = new ArrayMap<>();
        // Agrego token de usuario a los headers.
        mHeaders.put("auth", accessToken);
        // Si el token de usuario es vacio, simplemente se agrega un header vacio a la request.
        String jsonObjectString = null;
        if (jsonObject != null) {
            jsonObjectString = jsonObject.toString();
        }
        // Genero una GET o POST request dependiendo del requestType.
        if (requestType == AbstractRequest.RequestType.GET) {
            request = new GsonGetRequest(fullUrl, type, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return mHeaders;
                }
            };
        } else if (requestType == AbstractRequest.RequestType.POST) {
            request =
                    new GsonPostRequest(fullUrl, jsonObjectString, type, listener, errorListener) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return mHeaders;
                        }
                    };
            try {
                Log.d(TAG, "Request prepared.");
                Log.d(TAG, "Headers:");
                Log.d(TAG, request.getHeaders().toString());
                if (jsonObjectString != null) {
                    Log.d(TAG, "Body:");
                    Log.d(TAG, jsonObjectString);
                }
            } catch (AuthFailureError authFailureError) {
                Log.d(TAG, authFailureError.getStackTrace().toString());
            }
        }
        if (request != null) {
            request.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppRequestQueue.getInstance(context).addToRequestQueue(request);
        }
    }

    private String getAccesToken() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("access_token", "");
    }

}
