package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by ssainz on 10/25/16.
 */
public class UpdateDescriptionRequest<T> extends AbstractRequest<T> {

    private String description;
    private int extensionID;

    public UpdateDescriptionRequest(Context context, Type responseType) {
        super(context, responseType, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        return "/eventos/actualizardescripcionrecurso";
    }

    @Override
    protected final JsonObject getBody() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("descripcion", this.description);
        jsonObject.addProperty("id_extension", this.extensionID);
        return jsonObject;
    }

    public final void setAttributes(String description, int extensionID) {
        this.description = description;
        this.extensionID = extensionID;
    }
}
