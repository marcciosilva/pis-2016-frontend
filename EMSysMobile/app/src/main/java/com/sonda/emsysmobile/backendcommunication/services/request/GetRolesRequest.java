package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

import static com.sonda.emsysmobile.BuildConfig.GET_ROLES_BOTH_PATH;
import static com.sonda.emsysmobile.BuildConfig.GET_ROLES_EMPTY_PATH;
import static com.sonda.emsysmobile.BuildConfig.GET_ROLES_FAIL_PATH;
import static com.sonda.emsysmobile.BuildConfig.GET_ROLES_PATH;
import static com.sonda.emsysmobile.BuildConfig.GET_ROLES_RECURSOS_PATH;
import static com.sonda.emsysmobile.BuildConfig.GET_ROLES_ZONAS_PATH;

/**
 * Created by marccio on 10/3/16.
 */

public class GetRolesRequest<T> extends AbstractRequest<T> {

    private enum GetRolesCase {Both, Resources, Zones, Fail, Empty}

    private static final GetRolesCase GET_ROLES_CASE = GetRolesCase.Both;

    public GetRolesRequest(Context context, Type type) {
        super(context, type, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("debugMode", false);
        String path = null;
        if (!debugMode) {
            // Se envia request al path de getRoles que ofrece el web service.
            path = GET_ROLES_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (GET_ROLES_CASE) {
                case Both:
                    path = GET_ROLES_BOTH_PATH;
                    break;
                case Fail:
                    path = GET_ROLES_FAIL_PATH;
                    break;
                case Resources:
                    path = GET_ROLES_RECURSOS_PATH;
                    break;
                case Zones:
                    path = GET_ROLES_ZONAS_PATH;
                    break;
                case Empty:
                    path = GET_ROLES_EMPTY_PATH;
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
