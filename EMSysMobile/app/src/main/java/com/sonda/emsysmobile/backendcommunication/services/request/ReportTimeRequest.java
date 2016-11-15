package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by Pape on 10/30/2016.
 */

public class ReportTimeRequest<T> extends AbstractRequest {

    public static final String REPORTTIME_PATH = "/eventos/reportarhoraarribo?idExtension=";

    private int idExtension;

    public ReportTimeRequest(Context context, Type type, int idExtension) {
        super(context, type, RequestType.POST);
        this.idExtension = idExtension;
    }

    @Override
    protected final String getPath() {
        return REPORTTIME_PATH + Integer.toString(idExtension);
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }

}