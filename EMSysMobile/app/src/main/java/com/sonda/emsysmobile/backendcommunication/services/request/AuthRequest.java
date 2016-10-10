package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BuildConfig;

import java.lang.reflect.Type;

import static com.sonda.emsysmobile.BuildConfig.AUTH_ALREADY;
import static com.sonda.emsysmobile.BuildConfig.AUTH_CREDENTIALS_FAIL;
import static com.sonda.emsysmobile.BuildConfig.AUTH_PATH;
import static com.sonda.emsysmobile.BuildConfig.AUTH_SUCCESS_PATH;

/**
 * Created by mserralta on 1/10/16.
 */
public class AuthRequest<T> extends AbstractRequest<T> {

    private String user;
    private String password;
    private static final AuthCase AUTH_CASE = AuthCase.Success;
    private static final String TAG = AuthRequest.class.getName();

    private enum AuthCase {Success, CredentialsFail, AlreadyAuth}

    public AuthRequest(Context context, Type type) {
        super(context, type, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        boolean debugMode = BuildConfig.USING_MOCK_SERVER;
        String path = null;
        if (!debugMode) {
            path = AUTH_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (AUTH_CASE) {
                case Success:
                    // Siempre se obtiene una respuesta exitosa frente a login.
                    path = AUTH_SUCCESS_PATH;
                    break;
                case CredentialsFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    path = AUTH_CREDENTIALS_FAIL;
                    break;
                case AlreadyAuth:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    path = AUTH_ALREADY;
                    break;
                default:
                    break;
            }
        }
        return path;
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
