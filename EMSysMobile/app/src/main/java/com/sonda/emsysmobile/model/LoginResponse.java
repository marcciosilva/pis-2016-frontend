package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */
<<<<<<< HEAD
public class LoginResponse extends EmsysResponse {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("expires_in")
    public int expirationTime;

    public String getAccessToken() { return accessToken; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public int getExpirationTime() { return expirationTime; }

    public void setExpirationTime(int expirationTime) { this.expirationTime = expirationTime; }
=======

public class LoginResponse {

    @SerializedName("cod")
    public String codigoRespuesta;

    @SerializedName("response")
    public CustomResponse response;

    public class CustomResponse {
        @SerializedName("msg")
        public String errorMsg;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getErrorMessage() {
        if (response != null) {
            if (response.errorMsg != null) {
                return response.errorMsg;
            } else {
                return "";
            }
        } return "";
    }

>>>>>>> dev
}
