package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 8/30/16.
 */
public class AuthResponse extends EmsysResponse {

    @SerializedName("response")
    private AuthInnerResponse innerResponse;

    public final AuthInnerResponse getInnerResponse() {
        return innerResponse;
    }

    public final void setInnerResponse(AuthInnerResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    public final String getAccessToken() {
        if (innerResponse != null) {
            return innerResponse.getAccessToken();
        } else {
            return "";
        }
    }

    public final int getExpirationTime() {
        if (innerResponse != null) {
            return innerResponse.getExpirationTime();
        } else {
            return -1;
        }
    }
}
