package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 8/30/16.
 */
public class AuthResponse extends EmsysResponse {

    @SerializedName("response")
    private AuthInnerResponse innerResponse;

    public AuthInnerResponse getInnerResponse() {
        return innerResponse;
    }

    public void setInnerResponse(AuthInnerResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    public String getAccessToken() {
        if (innerResponse != null) {
            return innerResponse.getAccessToken();
        } else {
            return "";
        }
    }

    public int getExpirationTime() {
        if (innerResponse != null) {
            return innerResponse.getExpirationTime();
        } else {
            return -1;
        }
    }
}
