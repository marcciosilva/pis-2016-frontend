package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */

public class LoginResponse {

    @SerializedName("cod")
    private String mCodigoRespuesta;

    @SerializedName("response")
    private LoginInnerResponse mInnerResponse;

    public String getCodigoRespuesta() {
        return mCodigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        mCodigoRespuesta = codigoRespuesta;
    }

    public LoginInnerResponse getInnerResponse() {
        return mInnerResponse;
    }

    public void setResponse(LoginInnerResponse innerResponse) {
        mInnerResponse = innerResponse;
    }

    public String getErrorMessage() {
        if (mInnerResponse != null) {
            if (mInnerResponse.getErrorMsg() != null) {
                return mInnerResponse.getErrorMsg();
            } else {
                return "";
            }
        } return "";
    }

}
