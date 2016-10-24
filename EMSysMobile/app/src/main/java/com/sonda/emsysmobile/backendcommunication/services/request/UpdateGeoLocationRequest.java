package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;

import android.util.Log;

import java.lang.reflect.Type;

/**
 * Created by Pape on 20/10/2016.
 */

public class UpdateGeoLocationRequest<T> extends AbstractRequest {

    public static final String UPDATEGEOLOCATION_PATH = "/adjuntos/postgeoubicacion";
    private static final String TAG = LoginRequest.class.getName();

    private GeolocationDto geoLocation;

    public UpdateGeoLocationRequest(Context context, Type type, GeolocationDto geoLocation) {
        super(context, type, RequestType.POST);
        this.geoLocation = geoLocation;
    }

    @Override
    protected final String getPath() {
        return UPDATEGEOLOCATION_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        String json = new Gson().toJson(geoLocation);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);

        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        return jsonObject;
    }

}
