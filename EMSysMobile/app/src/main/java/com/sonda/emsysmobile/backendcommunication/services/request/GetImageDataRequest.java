package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by mserralta on 17/10/16.
 */

public class GetImageDataRequest<T> extends AbstractRequest<T> {

    private static final String TAG = GetImageDataRequest.class.getName();
    public static final String EVENT_DETAILS_PATH = "/users/getimagedata";

    private int mImageId;


    public GetImageDataRequest(Context context, Type responseType) {
        super(context, responseType, RequestType.GET);
    }
    @Override
    protected final String getPath() {
        return EVENT_DETAILS_PATH + "?idImagen=" + Integer.toString(mImageId);
    }

    @Override
    protected final JsonObject getBody() {
        return null;
    }

    public final void setAttributes(int imageId) {
        this.mImageId = imageId;
    }
}