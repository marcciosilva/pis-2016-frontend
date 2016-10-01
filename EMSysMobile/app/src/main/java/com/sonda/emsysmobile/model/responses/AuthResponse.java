package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 8/30/16.
 */
public class AuthResponse {

    @SerializedName("cod")
    private String mCodigoRespuesta;

    @SerializedName("response")
    private AuthInnerResponse mInnerResponse;

    public AuthInnerResponse getInnerResponse() {
        return mInnerResponse;
    }

    public void setResponse(AuthInnerResponse innerResponse) {
        mInnerResponse = innerResponse;
    }

    public String getCodigoRespuesta() {
        return mCodigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        mCodigoRespuesta = codigoRespuesta;
    }

    public String getAccessToken() {
        if (mInnerResponse != null) {
            return mInnerResponse.getAccessToken();
        } else {
            return "";
        }
    }

    public int getExpirationTime() {
        if (mInnerResponse != null) {
            return mInnerResponse.getExpirationTime();
        } else {
            return -1;
        }
    }

}
