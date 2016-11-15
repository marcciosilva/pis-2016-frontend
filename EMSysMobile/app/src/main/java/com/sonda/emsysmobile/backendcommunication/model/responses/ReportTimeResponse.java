package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pape on 10/30/2016.
 */

public class ReportTimeResponse extends EmsysResponse {

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
        if (!ReportTimeResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ReportTimeResponse other = (ReportTimeResponse) obj;
        return ((super.equals(obj)) && (innerResponse.equals(other.innerResponse)));

    }
}

