package com.sonda.emsysmobile.model.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by marccio on 9/28/16.
 */

public abstract class DtoRol implements Serializable {

    private ArrayList<DtoZona> mZonas;
    private ArrayList<DtoRecurso> mRecursos ;

    public DtoRol() {
        mZonas = new ArrayList<>();
        mRecursos = new ArrayList<>();
    }

    public DtoRol(ArrayList<DtoZona> zonas, ArrayList<DtoRecurso> recursos) {
        mZonas = zonas;
        mRecursos = recursos;
    }

    public ArrayList<DtoZona> getZonas() {
        return mZonas;
    }

    public ArrayList<DtoRecurso> getRecursos() {
        return mRecursos;
    }
}
