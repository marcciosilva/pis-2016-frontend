package com.sonda.emsysmobile.services.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.Since;
import com.sonda.emsysmobile.model.AuthResponse;
import com.android.volley.Response;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by mserralta on 1/10/16.
 */
public class AuthRequest<T> extends AbstractRequest<T> {
    private String user;
    private String password;

    private static final String TAG = AuthRequest.class.getName();

    public AuthRequest(Context context, Type type) {
        super(context, type);
    }

    @Override
    protected String getPath() {
        return "/users/authenticate";
    }

    @Override
    protected JsonObject getBody() {
        JsonObject body = new JsonObject(); 
        body.addProperty("password", password);
        body.addProperty("username", user);

        Log.d(TAG, "Request body:");
        Log.d(TAG, body.toString());
        return body;
    }

    @Override
    protected Response.Listener getListener() {
        return null;
    }



    public void setAttributes(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
