package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.model.core.DtoRol;

/**
 * Created by marccio on 9/28/16.
 */

public class GetRolesResponse {

    @SerializedName("cod")
    public String mCodigoRespuesta;

    @SerializedName("response")
    public DtoRol mRoles;

    public String getCodigoRespuesta() {
        return mCodigoRespuesta;
    }

    public DtoRol getRoles() {
        return mRoles;
    }
}
