package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import java.lang.reflect.Type;

/**
 * Created by mserralta on 1/10/16.
 */
public class AuthRequest<T> extends AbstractRequest<T> {

    public static final String AUTH_PATH = "/users/authenticate";
    private static final String TAG = AuthRequest.class.getName();

    private String user;
    private String password;

    public AuthRequest(Context context, Type type) {
        super(context, type, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        return AUTH_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        JsonObject body = new JsonObject();
        body.addProperty("password", password);
        body.addProperty("username", user);

        Log.d(TAG, "Request body:");
        Log.d(TAG, body.toString());
        return body;
    }


    public final void setAttributes(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
