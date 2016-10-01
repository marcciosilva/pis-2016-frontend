package com.sonda.emsysmobile.model.core;

import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;
import com.sonda.emsysmobile.model.core.DtoZona;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class DtoRolUnitTest {

    private DtoRol mDtoRol;
    private DtoZona mZona1;
    private DtoZona mZona2;
    private DtoRecurso mRecurso1;
    private DtoRecurso mRecurso2;
    private ArrayList<DtoZona> mZonas;
    ArrayList<DtoRecurso> mRecursos;

    @Before
    public void executeBeforeEach() {
        DtoZona mZona1 = new DtoZona("zona 1", "1234", "ue1");
        DtoZona mZona2 = new DtoZona("zona 2", "8262", "ue2");
        DtoRecurso mRecurso1 = new DtoRecurso("A123");
        DtoRecurso mRecurso2 = new DtoRecurso("B621");
        mZonas = new ArrayList<>();
        mZonas.add(mZona1);
        mZonas.add(mZona2);
        mRecursos = new ArrayList<>();
        mRecursos.add(mRecurso1);
        mRecursos.add(mRecurso2);
        mDtoRol = new DtoRol(mZonas, mRecursos);
    }

    @Test
    public void testGetZonas() {
        ArrayList<DtoZona> zonas = mDtoRol.getZonas();
        assertTrue(zonas.equals(mZonas));
    }

    @Test
    public void testGetRecursos() {
        ArrayList<DtoRecurso> recursos = mDtoRol.getRecursos();
        assertTrue(recursos.equals(mRecursos));
    }

    @Test
    public void testSetZonas() {
        mZonas.add(new DtoZona("zona 3", "5312", "ue1"));
        mDtoRol.setZonas(mZonas);
        assertTrue(mDtoRol.getZonas().equals(mZonas));
    }

    @Test
    public void testSetRecursos() {
        mRecursos.add(new DtoRecurso("9875"));
        mDtoRol.setRecursos(mRecursos);
        assertTrue(mDtoRol.getRecursos().equals(mRecursos));
    }

}
