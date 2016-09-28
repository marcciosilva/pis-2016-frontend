package com.sonda.emsysmobile.model.core;

import java.io.Serializable;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoZona {

    private String mNombre;

    public DtoZona(String nombre) {
        mNombre = nombre;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

}
