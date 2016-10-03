package com.sonda.emsysmobile.network.services.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.network.services.endpoint.EndpointService;

import java.lang.reflect.Type;

/**
 * Created by mserralta on 1/10/16.
 */
public abstract class AbstractRequest<T> {


    private static final String TAG = AbstractRequest.class.getName();
    protected Context context;
    protected Type responseType;

    protected Response.Listener<T> listener;
    protected Response.ErrorListener errorListener;

    public enum RequestType {GET, POST}
    private RequestType requestType;

    public AbstractRequest(Context context, Type responseType, RequestType requestType){
        this.context = context;
        this.responseType = responseType;
        this.requestType = requestType;
    }

    public void execute() {
        String path = getPath();
        JsonObject jsonObject = getBody();
        Response.Listener listener = getListener();
        Response.ErrorListener errorListener = getErrorListener();

        EndpointService endpointService = new EndpointService(context);
        endpointService.execute(requestType,path,jsonObject, responseType,listener, errorListener);
    }

    public void setListener(Response.Listener<T> listener){
        this.listener = listener;
    }

    protected Response.Listener<T> getListener(){
        return this.listener;
    }

    public void setErrorListener(Response.ErrorListener errorListener){
        this.errorListener = errorListener;
    }

    protected Response.ErrorListener getErrorListener(){
        if(errorListener == null){
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error en la comunicación con el servidor.");
                }
            };
        }
        return errorListener;
    }

    abstract protected String getPath();
    abstract protected JsonObject getBody();



}
