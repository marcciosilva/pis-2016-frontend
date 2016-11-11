package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nachoprbd on 16/10/2016.
 */
public class KeepAliveResponse extends EmsysResponse {

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
        if (!KeepAliveResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final KeepAliveResponse other = (KeepAliveResponse) obj;
        if (innerResponse.getMsg() != other.innerResponse.getMsg()) {
            return false;
        }
        return this.getCode() == other.getCode();
    }

}

