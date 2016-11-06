package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by sasainz on 11/5/16.
 */

public class SendNotificationTokenRequest<T> extends AbstractRequest<T> {

    private String token;

    public SendNotificationTokenRequest(Context context, Type responseType) {
        super(context, responseType, RequestType.POST);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected String getPath() {
        return "/users/SetRegistrationToken";
    }

    @Override
    protected JsonObject getBody() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("registrationTokens", this.token);
        return jsonObject;
    }
}
