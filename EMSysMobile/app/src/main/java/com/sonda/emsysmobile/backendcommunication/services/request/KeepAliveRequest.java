package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by nachoprbd on 16/10/2016.
 */
public class KeepAliveRequest<T> extends AbstractRequest<T> {

    public static final String KEEP_ALIVE_PATH = "/users/expiration_time";

    public KeepAliveRequest(Context context, Type type) {
        super(context, type, RequestType.GET);
    }

    @Override
    protected final String getPath() {
        return KEEP_ALIVE_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }
}
