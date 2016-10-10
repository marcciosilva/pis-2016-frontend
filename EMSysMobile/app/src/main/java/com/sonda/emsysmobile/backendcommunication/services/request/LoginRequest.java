package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.logic.model.core.RoleDto;

import java.lang.reflect.Type;

import static com.sonda.emsysmobile.BuildConfig.LOGIN_FAIL_PATH;
import static com.sonda.emsysmobile.BuildConfig.LOGIN_PATH;
import static com.sonda.emsysmobile.BuildConfig.LOGIN_SUCCESS_PATH;

/**
 * Created by marccio on 10/3/16.
 */

public class LoginRequest<T> extends AbstractRequest<T> {

    private static final LoginCase LOGIN_CASE = LoginCase.Fail;
    private RoleDto roles;
    private static final String TAG = LoginRequest.class.getName();

    private enum LoginCase {Success, Fail}

    public LoginRequest(Context context, Type type, RoleDto roles) {
        super(context, type, RequestType.POST);
        this.roles = roles;
    }

    @Override
    protected final String getPath() {
        boolean debugMode = BuildConfig.USING_MOCK_SERVER;
        String path = null;
        if (!debugMode) {
            // Se envia request al path de getRoles que ofrece el web service.
            path = LOGIN_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (LOGIN_CASE) {
                case Success:
                    path = LOGIN_SUCCESS_PATH;
                    break;
                case Fail:
                    path = LOGIN_FAIL_PATH;
                    break;
                default:
                    break;
            }
        }
        return path;
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
