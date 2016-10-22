package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by marccio on 10/3/16.
 */

public class LogoutRequest<T> extends AbstractRequest<T> {

    public static final String LOGOUT_PATH = "/users/logout";

    public LogoutRequest(Context context, Type type) {
        super(context, type, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        return LOGOUT_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }
}
