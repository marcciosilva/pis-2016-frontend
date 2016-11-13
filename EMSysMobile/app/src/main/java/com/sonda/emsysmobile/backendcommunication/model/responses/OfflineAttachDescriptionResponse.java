package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pape on 10/30/2016.
 */

public class OfflineAttachDescriptionResponse extends EmsysResponse{

    @SerializedName("response")
    private ErrorResponse innerResponse;

    public final void setInnerResponse(ErrorResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    public final ErrorResponse getInnerResponse() {
        return innerResponse;
    }

}

