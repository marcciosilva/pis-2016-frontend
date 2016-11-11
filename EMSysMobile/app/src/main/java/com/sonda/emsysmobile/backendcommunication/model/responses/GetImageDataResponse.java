package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;

import java.util.List;

/**
 * Created by ssainz on 9/25/16.
 */
public class GetImageDataResponse extends EmsysResponse {

    /**
     * The list of events returned by the service
     */
    @SerializedName("response")
    private ImageDataDto imageData;

    public GetImageDataResponse(ImageDataDto imageDataDto){
        this.imageData = imageDataDto;
    }

    public ImageDataDto getImageData() {
        return imageData;
    }
}