package com.sonda.emsysmobile.model.core;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class DtoZonaUnitTest {

    private DtoZona mDtoZona;

    @Before
    public void executeBeforeEach() {
        mDtoZona = new DtoZona("zona 1", "1", "ue1");
    }

    @Test
    public void testGetNombre() {
        assertTrue(mDtoZona.getNombre().equals("zona 1"));
    }

    @Test
    public void testGetId() {
        assertTrue(mDtoZona.getId().equals("1"));
    }

    @Test
    public void testGetNombreUnidadEjecutora() {
        assertTrue(mDtoZona.getNombreUnidadEjecutora().equals("ue1"));
    }

    @Test
    public void testSetNombre() {
        mDtoZona.setNombre("zona 2");
        assertTrue(mDtoZona.getNombre().equals("zona 2"));
    }

    @Test
    public void testSetId() {
        mDtoZona.setId("2");
        assertTrue(mDtoZona.getId().equals("2"));
    }

    @Test
    public void testSetNombreUnidadEjecutora() {
        mDtoZona.setNombreUnidadEjecutora("ue2");
        assertTrue(mDtoZona.getNombreUnidadEjecutora().equals("ue2"));
    }


    @Test
    public void testEquals_null() {
        DtoZona zona = new DtoZona(null, null, null);
        assertFalse(zona.equals(null));
    }

    @Test
    public void testEquals_differentClass() {
        DtoZona zona = new DtoZona(null, null, null);
        DtoRecurso recurso = new DtoRecurso(null);
        assertFalse(zona.equals(recurso));
    }

    @Test
    public void testEquals_differentNombre() {
        DtoZona zona1 = new DtoZona("1", "1", "1");
        DtoZona zona2 = new DtoZona("2", "1", "1");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void testEquals_differentId() {
        DtoZona zona1 = new DtoZona("1", "1", "1");
        DtoZona zona2 = new DtoZona("1", "2", "1");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void testEquals_differentNombreUnidadEjecutora() {
        DtoZona zona1 = new DtoZona("1", "1", "1");
        DtoZona zona2 = new DtoZona("1", "1", "2");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void testEquals_equals() {
        DtoZona zona1 = new DtoZona("1", "1", "1");
        DtoZona zona2 = new DtoZona("1", "1", "1");
        assertTrue(zona1.equals(zona2));
    }

}
