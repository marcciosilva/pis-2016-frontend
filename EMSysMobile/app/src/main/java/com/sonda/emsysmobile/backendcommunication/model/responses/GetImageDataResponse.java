package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;

/**
 * Created by ssainz on 9/25/16.
 */
public class GetImageDataResponse extends EmsysResponse {

    /**
     * The list of events returned by the service
     */
    @SerializedName("response")
    private ImageDataDto imageData;

    public GetImageDataResponse(ImageDataDto imageData) {
        this.imageData = imageData;
    }

    public final ImageDataDto getImageData() {
        return imageData;
    }
}