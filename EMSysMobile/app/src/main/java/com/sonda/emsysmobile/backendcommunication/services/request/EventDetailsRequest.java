package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by mserralta on 17/10/16.
 */

public class EventDetailsRequest<T> extends AbstractRequest<T> {

    public static final String EVENT_DETAILS_PATH = "/eventos/obtener";
    private int eventId;

    public EventDetailsRequest(Context context, Type responseType) {
        super(context, responseType, RequestType.GET);
    }

    @Override
    protected final String getPath() {
        return EVENT_DETAILS_PATH + "?idEvento=" + Integer.toString(eventId);
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }

    public final void setAttributes(int eventId) {
        this.eventId = eventId;
    }
}