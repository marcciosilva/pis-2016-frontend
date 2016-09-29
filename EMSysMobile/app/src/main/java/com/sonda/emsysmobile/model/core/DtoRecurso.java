package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoRecurso implements Serializable {

    @SerializedName("codigo")
    private String mCodigo;

    public DtoRecurso(String mCodigo) {
        this.mCodigo = mCodigo;
    }

    public String getCodigo() {
        return mCodigo;
    }

}
