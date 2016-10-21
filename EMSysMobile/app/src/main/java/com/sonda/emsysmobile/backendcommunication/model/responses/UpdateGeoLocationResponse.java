package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pape on 20/10/2016.
 */

public class UpdateGeoLocationResponse extends EmsysResponse{

    @SerializedName("response")
    private ErrorResponse innerResponse;

    public final ErrorResponse getInnerResponse() {
        return innerResponse;
    }

    public final void setInnerResponse(ErrorResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    @Override
    public final boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!UpdateGeoLocationResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final UpdateGeoLocationResponse other = (UpdateGeoLocationResponse) obj;
        return ((super.equals(obj)) && (innerResponse.equals(other.innerResponse)));

    }
}
