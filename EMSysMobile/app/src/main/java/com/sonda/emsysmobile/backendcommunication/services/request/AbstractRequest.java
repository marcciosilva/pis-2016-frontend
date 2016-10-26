package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.backendcommunication.services.endpoint.EndpointService;

import java.lang.reflect.Type;

/**
 * Created by mserralta on 1/10/16.
 */
public abstract class AbstractRequest<T> {

    private static final String TAG = AbstractRequest.class.getName();
    private Context context;
    private Type responseType;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorListener;
    private RequestType requestType;

    public enum RequestType {GET, POST}

    public AbstractRequest(Context context, Type responseType, RequestType requestType) {
        this.context = context;
        this.responseType = responseType;
        this.requestType = requestType;
    }

    public final void execute() {
        String baseURL = getBaseURL();
        String path = getPath();
        JsonObject jsonObject = getBody();
        Response.Listener listener = getListener();
        Response.ErrorListener errorListener = getErrorListener();

        EndpointService endpointService = new EndpointService(context);
        endpointService.execute(baseURL, requestType, path, jsonObject, responseType, listener, errorListener);
    }

    public final Context getContext() {
        return context;
    }

    public final void setContext(Context context) {
        this.context = context;
    }

    public final Type getResponseType() {
        return responseType;
    }

    public final void setResponseType(Type responseType) {
        this.responseType = responseType;
    }

    public final void setListener(Response.Listener<T> listener) {
        this.listener = listener;
    }

    protected final Response.Listener<T> getListener() {
        return this.listener;
    }

    public final void setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    protected final Response.ErrorListener getErrorListener() {
        if (errorListener == null) {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error en la comunicación con el servidor.");
                }
            };
        }
        return errorListener;
    }

    protected abstract String getPath();

    protected abstract JsonObject getBody();

    /**
     * If some request needs a different URL can Override this method.
     * Can be used to set different environment just for one Request.
     *
     * @return A string with the BaseURL for the request.
     */
    protected String getBaseURL() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString("backendUrl", BuildConfig.BASE_URL);
    }
}
