package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by marccio on 9/28/16.
 */

public class GetRolesResponse {

    @SerializedName("cod")
    public String codigoRespuesta;

    @SerializedName("roles")
    public Roles roles;

    public class Roles {

//        @SerializedName("roles")
        @SerializedName
        public List<DtoZona> zonas = new ArrayList<>();
        public List<DtoRecurso> recursos = new ArrayList<>();
    }

//    public class Rol {
//
//        @SerializedName("tipo")
//        public String tipo;
//
//        @SerializedName("id")
//        public String id;
//    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

//    public List<Rol> getRoles() {
//        // Se asegura que no se maneje una lista null.
//        if (response.roleSet == null) {
//            response.roleSet = new ArrayList<>();
//        }
//        return response.roleSet;
//    }


    public String getAccessToken() {
        if (response != null) {
            return response.accessToken;
        } else {
            return "";
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
