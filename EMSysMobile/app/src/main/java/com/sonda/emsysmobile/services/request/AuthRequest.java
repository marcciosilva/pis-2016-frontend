package com.sonda.emsysmobile.services.request;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by mserralta on 1/10/16.
 */
public class AuthRequest<T> extends AbstractRequest<T> {
    
    private String user;
    private String password;

    public static final String EVENTS_PATH = "/events";
    public static final String AUTH_PATH = "/users/authenticate";
    public static final String GET_ROLES_PATH = "/users/getroles";
    public static final String LOGIN_PATH = "/users/login";
    // Con el fin de probar cada caso de auth con Mock Server:
    public static final String AUTH_SUCCESS_PATH = "/users/success";
    public static final String AUTH_USERNAME_FAIL_PATH = "/users/username-fail";
    public static final String AUTH_PASSWORD_FAIL_PATH = "/users/pass-fail";

    private enum AuthCase {Success, UsernameFail, PassFail}
    private static final AuthCase authCase = AuthCase.Success;

    private static final String TAG = AuthRequest.class.getName();

    public AuthRequest(Context context, Type type) {
        super(context, type);
    }

    @Override
    protected String getPath() {
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String path =  AUTH_SUCCESS_PATH;
        if (!debugMode) {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (authCase) {
                case Success:
                    // Siempre se obtiene una respuesta exitosa frente a login.
                    path = AUTH_SUCCESS_PATH;
                    break;
                case UsernameFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    path = AUTH_USERNAME_FAIL_PATH;
                    break;
                case PassFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    path = AUTH_PASSWORD_FAIL_PATH;
                    break;
                default:
                    break;
            }

        }
        return path;
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


    public void setAttributes(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
