package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssainz on 8/30/16.
 */
public class LoginResponse {

    @SerializedName("cod")
    public int codigoRespuesta;

    @SerializedName("response")
    public CustomResponse response;

    public class CustomResponse {
        @SerializedName("access_token")
        public String accessToken;

        @SerializedName("expires_in")
        public int expirationTime;

        @SerializedName("roles")
        public List<Rol> roleSet = new ArrayList<Rol>();
    }

    public class Rol {

        @SerializedName("tipo")
        public String tipo;

        @SerializedName("id")
        public int id;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public List<Rol> getRoles() {
        return response.roleSet;
    }


    public String getAccessToken() {
        if (response != null) {
            return response.accessToken;
        } else {
            return null;
        }
    }

    public int getExpirationTime() {
        if (response != null) {
            return response.expirationTime;
        } else {
            return -1;
        }
    }

}
