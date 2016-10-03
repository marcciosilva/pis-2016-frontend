package com.sonda.emsysmobile.services.endpoint;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.model.responses.AuthResponse;
import com.sonda.emsysmobile.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;

import java.lang.reflect.Type;
import java.util.Map;

import static com.sonda.emsysmobile.BuildConfig.BASE_MOCK_URL;
import static com.sonda.emsysmobile.utils.JsonUtils.jsonToUrlEncodedString;

/**
 * Created by mserralta on 29/9/16.
 */
public class EndpointService<T> {

    private Context context;

    public EndpointService(Context context) {
        this.context = context;
    }

    public void execute(int method, String path, JsonObject jsonObject, Type type, Response.Listener listener, Response.ErrorListener errorListener) {
        boolean debugMode = BuildConfig.USING_MOCK_SERVER;
        // Se construye la URL de la request.
        String url;
        if (!debugMode) {
            url = BuildConfig.BASE_URL;
        } else {
            url = BASE_MOCK_URL;
        }
        url += path;
        // Se imprime body en json a enviar y la IP a la que va dirigida la request.
        System.out.println(jsonObject.toString());
        Log.d("IP_REQUEST", url);
        // Se obtiene el token de autenticacion.
        String accessToken = getAccesToken();
        GsonPostRequest<T> request;
        // Estructura encargada de llevar los headers de la request.
        final Map<String, String> mHeaders = new ArrayMap<>();
        // Agrego token de usuario a los headers.
        mHeaders.put("auth", accessToken);
        // Si el token de usuario es vacio, simplemente se agrega un header vacio a la request.
        request = new GsonPostRequest(url, jsonObject.toString(), type, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeaders;
            }
        };
        AppRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    private String getAccesToken() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("access_token", "");
    }

}
