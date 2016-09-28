package com.sonda.emsysmobile.model.core;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoRecurso {

    private String mCodigo;

    public DtoRecurso(String codigo) {
        mCodigo = codigo;
    }

    public String getCodigo() {
        return mCodigo;
    }

    public void setCodigo(String codigo) {
        mCodigo = codigo;
    }
}
