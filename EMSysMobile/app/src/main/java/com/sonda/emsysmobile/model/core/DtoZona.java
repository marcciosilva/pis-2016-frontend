package com.sonda.emsysmobile.model.core;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoZona extends DtoRol {

    private String mNombre;

    public DtoZona(String nombre) {
        super();
        mNombre = nombre;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

}
