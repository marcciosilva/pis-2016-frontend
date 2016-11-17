package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by sasainz on 11/2/16.
 */

public class AttachImageRequest<T> extends AbstractRequest<T> {

    public static final String UPLOAD_IMAGE_PATH = "/adjuntos/adjuntarimagen";
    private String name;
    private String image;
    private int extensionId;

    public AttachImageRequest(Context context, Type responseType) {
        super(context, responseType, RequestType.POST);
    }

    @Override
    protected final String getPath() {
        return UPLOAD_IMAGE_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nombre", this.name);
        jsonObject.addProperty("file_data", this.image);
        jsonObject.addProperty("id_extension", this.extensionId);
        return jsonObject;
    }

    public final void setAttributes(int extensionId, String name, String image) {
        this.extensionId = extensionId;
        this.name = name;
        this.image = image;
    }
}
