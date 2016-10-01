package com.sonda.emsysmobile.model.core;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoError extends DtoRespuesta {

    private String mCodigo;

    public DtoError(String codigo) {
        super();
        mCodigo = codigo;
    }

    public String getCodigo() {
        return mCodigo;
    }

    public void setCodigo(String codigo) {
        mCodigo = codigo;
    }

}
