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

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setNombreUnidadEjecutora(String nombreUnidadEjecutora) {
        mNombreUnidadEjecutora = nombreUnidadEjecutora;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!DtoZona.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final DtoZona other = (DtoZona) obj;
        if (mNombre != other.mNombre || mId != other.mId
                || mNombreUnidadEjecutora != other.mNombreUnidadEjecutora) {
            return false;
        }
        return true;
    }

}
