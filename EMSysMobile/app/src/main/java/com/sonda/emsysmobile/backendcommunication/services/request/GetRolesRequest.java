package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by marccio on 10/3/16.
 */

public class GetRolesRequest<T> extends AbstractRequest<T> {

    public static final String GET_ROLES_PATH = "/users/getroles";

    public GetRolesRequest(Context context, Type type) {
        super(context, type, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        return GET_ROLES_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }
}
