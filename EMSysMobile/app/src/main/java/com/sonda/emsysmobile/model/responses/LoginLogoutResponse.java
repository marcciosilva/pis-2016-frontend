package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */

public class LoginLogoutResponse extends EmsysResponse {

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
        if (!LoginLogoutResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final LoginLogoutResponse other = (LoginLogoutResponse) obj;
        return ((super.equals(obj)) && (innerResponse.equals(other.innerResponse)));
    }

}
