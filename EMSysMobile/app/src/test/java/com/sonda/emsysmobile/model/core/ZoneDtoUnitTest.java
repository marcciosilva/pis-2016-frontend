package com.sonda.emsysmobile.model.core;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class ZoneDtoUnitTest {

    private ZoneDto mZoneDto;

    @Before
    public void executeBeforeEach() {
        mZoneDto = new ZoneDto("zona 1", "1", "ue1");
    }

    @Test
    public void testGetNombre() {
        assertTrue(mZoneDto.getName().equals("zona 1"));
    }

    @Test
    public void testGetId() {
        assertTrue(mZoneDto.getIdentifier().equals("1"));
    }

    @Test
    public void testGetNombreUnidadEjecutora() {
        assertTrue(mZoneDto.getExecUnitName().equals("ue1"));
    }

    @Test
    public void testSetNombre() {
        mZoneDto.setName("zona 2");
        assertTrue(mZoneDto.getName().equals("zona 2"));
    }

    @Test
    public void testSetId() {
        mZoneDto.setIdentifier("2");
        assertTrue(mZoneDto.getIdentifier().equals("2"));
    }

    @Test
    public void testSetNombreUnidadEjecutora() {
        mZoneDto.setExecUnitName("ue2");
        assertTrue(mZoneDto.getExecUnitName().equals("ue2"));
    }

    @Test
    public void testEquals_null() {
        ZoneDto zona = new ZoneDto(null, null, null);
        assertFalse(zona.equals(null));
    }

    @Test
    public void testEquals_differentClass() {
        ZoneDto zona = new ZoneDto(null, null, null);
        ResourceDto recurso = new ResourceDto(null);
        assertFalse(zona.equals(recurso));
    }

    @Test
    public void testEquals_differentNombre() {
        ZoneDto zona1 = new ZoneDto("1", "1", "1");
        ZoneDto zona2 = new ZoneDto("2", "1", "1");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void testEquals_differentId() {
        ZoneDto zona1 = new ZoneDto("1", "1", "1");
        ZoneDto zona2 = new ZoneDto("1", "2", "1");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void testEquals_differentNombreUnidadEjecutora() {
        ZoneDto zona1 = new ZoneDto("1", "1", "1");
        ZoneDto zona2 = new ZoneDto("1", "1", "2");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void testEquals_equals() {
        ZoneDto zona1 = new ZoneDto("1", "1", "1");
        ZoneDto zona2 = new ZoneDto("1", "1", "1");
        assertTrue(zona1.equals(zona2));
    }
}
