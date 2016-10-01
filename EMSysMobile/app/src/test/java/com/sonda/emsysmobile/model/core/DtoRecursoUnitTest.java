package com.sonda.emsysmobile.model.core;

import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class DtoRecursoUnitTest {

    private DtoRecurso mDtoRecurso;

    @Before
    public void executeBeforeEach() {
        mDtoRecurso = new DtoRecurso("1");
    }

    @Test
    public void testGetCodigo() {
        assertTrue(mDtoRecurso.getCodigo().equals("1"));
    }

    @Test
    public void testSetCodigo() {
        mDtoRecurso.setCodigo("4");
        assertTrue(mDtoRecurso.getCodigo().equals("4"));
    }

    @Test
    public void testEquals_null() {
        DtoRecurso recurso = new DtoRecurso(null);
        assertFalse(recurso.equals(null));
    }

    @Test
    public void testEquals_differentClass() {
        DtoRecurso recurso = new DtoRecurso(null);
        DtoRol rol = new DtoRol(null, null);
        assertFalse(recurso.equals(rol));
    }

    @Test
    public void testEquals_differentCodigo() {
        DtoRecurso recurso1 = new DtoRecurso("1");
        DtoRecurso recurso2 = new DtoRecurso("2");
        assertFalse(recurso1.equals(recurso2));
    }

    @Test
    public void testEquals_equals() {
        DtoRecurso recurso1 = new DtoRecurso("1");
        DtoRecurso recurso2 = new DtoRecurso("1");
        assertTrue(recurso1.equals(recurso2));
    }

}
