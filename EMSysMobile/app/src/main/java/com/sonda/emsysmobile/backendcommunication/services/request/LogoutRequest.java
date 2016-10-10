package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BuildConfig;

import java.lang.reflect.Type;

import static com.sonda.emsysmobile.BuildConfig.LOGOUT_COD_2_PATH;
import static com.sonda.emsysmobile.BuildConfig.LOGOUT_COD_5_PATH;
import static com.sonda.emsysmobile.BuildConfig.LOGOUT_PATH;
import static com.sonda.emsysmobile.BuildConfig.LOGOUT_SUCCESS_PATH;

/**
 * Created by marccio on 10/3/16.
 */

public class LogoutRequest<T> extends AbstractRequest<T> {

    private static final LogoutCase LOGOUT_CASE = LogoutCase.Success;

    private enum LogoutCase {Success, Cod2, Cod5}

    public LogoutRequest(Context context, Type type) {
        super(context, type, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        boolean debugMode = BuildConfig.USING_MOCK_SERVER;
        String path = null;
        if (!debugMode) {
            // Se envia request al path de getRoles que ofrece el web service.
            path = LOGOUT_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (LOGOUT_CASE) {
                case Success:
                    path = LOGOUT_SUCCESS_PATH;
                    break;
                case Cod2:
                    path = LOGOUT_COD_2_PATH;
                    break;
                case Cod5:
                    path = LOGOUT_COD_5_PATH;
                    break;
                default:
                    break;
            }
        }
        return path;
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }
}
