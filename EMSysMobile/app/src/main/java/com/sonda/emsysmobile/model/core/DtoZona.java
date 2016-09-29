package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoZona implements Serializable {

    @SerializedName("nombre")
    private String mNombre;
    @SerializedName("id")
    private String mId;
    @SerializedName("nombreUnidadEjecutora")
    private String mNombreUnidadEjecutora;

    public DtoZona(String mNombre, String mId, String mNombreUnidadEjecutora) {
        this.mNombre = mNombre;
        this.mId = mId;
        this.mNombreUnidadEjecutora = mNombreUnidadEjecutora;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getId() {
        return mId;
    }

    public String getNombreUnidadEjecutora() {
        return mNombreUnidadEjecutora;
    }

}
