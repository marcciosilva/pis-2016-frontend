package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */

public class LoginLogoutResponse extends EmsysResponse {

    @SerializedName("response")
    private LoginLogoutInnerResponse innerResponse;

    public LoginLogoutInnerResponse getInnerResponse() {
        return innerResponse;
    }

    public void setInnerResponse(LoginLogoutInnerResponse innerResponse) {
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
