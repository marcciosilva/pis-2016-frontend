package com.sonda.emsysmobile.network.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

import static com.sonda.emsysmobile.BuildConfig.*;

/**
 * Created by marccio on 10/3/16.
 */

public class EventsRequest<T> extends AbstractRequest<T> {

    public EventsRequest(Context context, Type type) {
        super(context, type);
    }

    @Override
    protected String getPath() {
        return BASE_URL + EVENTS_PATH;
    }

    @Override
    protected JsonObject getBody() {
        return null;
    }
}
