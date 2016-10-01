package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */

public class LoginResponse extends EmsysResponse {

    @SerializedName("response")
    private LoginInnerResponse innerResponse;

    public LoginInnerResponse getInnerResponse() {
        return innerResponse;
    }

    public void setInnerResponse(LoginInnerResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    public String getErrorMessage() {
        if (innerResponse != null) {
            if (innerResponse.getErrorMsg() != null) {
                return innerResponse.getErrorMsg();
            } else {
                return "";
            }
        } return "";
    }
}
