package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/28/16.
 */

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

}
