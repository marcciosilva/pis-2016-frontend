package com.sonda.emsysmobile.backendcommunication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class GsonGetRequest<T> extends Request<T> {
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;

    public static final String TAG = "VolleyRequest";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSS";

    public GsonGetRequest
            (
                    @NonNull final String url,
                    @NonNull final Type type,
                    @NonNull final Response.Listener<T> listener,
                    @NonNull final Response.ErrorListener errorListener
            ) {
        super(Method.GET, url, errorListener);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);

        this.gson = gsonBuilder.create();
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected final void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected final Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d(TAG, "\n ------------------------------ \n RESPONSE from :" + getUrl() + "\n" +
                    json + "\n------------------------------\n");
            return (Response<T>) Response.success(gson.fromJson(json, type), HttpHeaderParser
                    .parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.d(TAG, "Error en la sintaxis del mensaje recibido. Chequear JSON.");
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}
