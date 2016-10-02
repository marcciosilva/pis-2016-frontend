package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */

public class LoginLogoutResponse extends EmsysResponse {

    @SerializedName("response")
    private ErrorResponse innerResponse;

    public ErrorResponse getInnerResponse() {
        return innerResponse;
    }

    public void setInnerResponse(ErrorResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

}
