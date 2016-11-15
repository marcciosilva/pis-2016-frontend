package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;

import java.lang.reflect.Type;

/**
 * Created by jmsmuy on 15/10/16.
 */

public class ExternalServiceRequest<T> extends AbstractRequest<T> {

    public static final String CONSUME_WS_PATH = "/servicioexterno";
    private static final String TAG = ExternalServiceRequest.class.getName();

    private ExternalServiceQueryDto externalServiceQueryDto;

    public ExternalServiceRequest(Context context, Type type,
                                  ExternalServiceQueryDto externalServiceQueryDto) {
        super(context, type, RequestType.POST);
        this.externalServiceQueryDto = externalServiceQueryDto;
    }

    @Override
    protected final String getPath() {
        return CONSUME_WS_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        // Se convierten los parametros del request para el servicio externo en un objeto de json.
        String json = new Gson().toJson(externalServiceQueryDto);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        // Se imprime string en json.
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        return jsonObject;
    }
}
