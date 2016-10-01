package com.sonda.emsysmobile.model.core;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoOk extends DtoRespuesta {

    private String mNombre;

    public DtoOk(String nombre) {
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
