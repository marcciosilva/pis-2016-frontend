package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by marccio on 9/28/16.
 */

public class DtoRol implements Serializable {

    @SerializedName("zonas")
    private ArrayList<DtoZona> mZonas = new ArrayList<>();
    @SerializedName("recursos")
    private ArrayList<DtoRecurso> mRecursos = new ArrayList<>();

//    public DtoRol() {
//        mZonas = new ArrayList<>();
//        mRecursos = new ArrayList<>();
//    }
//
//    public DtoRol(ArrayList<DtoZona> zonas, ArrayList<DtoRecurso> recursos) {
//        mZonas = zonas;
//        mRecursos = recursos;
//    }

    public ArrayList<DtoZona> getZonas() {
        return mZonas;
    }

    public ArrayList<DtoRecurso> getRecursos() {
        return mRecursos;
    }
}
