package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.logic.model.core.RoleDto;

import java.lang.reflect.Type;

/**
 * Created by marccio on 10/3/16.
 */

public class LoginRequest<T> extends AbstractRequest<T> {

    public static final String LOGIN_PATH = "/users/login";
    private static final String TAG = LoginRequest.class.getName();

    private RoleDto roles;

    public LoginRequest(Context context, Type type, RoleDto roles) {
        super(context, type, RequestType.POST);
        this.roles = roles;
    }

    @Override
    protected final String getPath() {
        return LOGIN_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        // Se convierten los roles en un objeto de json.
        String json = new Gson().toJson(roles);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        // Se imprime string en json.
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        return jsonObject;
    }
}
