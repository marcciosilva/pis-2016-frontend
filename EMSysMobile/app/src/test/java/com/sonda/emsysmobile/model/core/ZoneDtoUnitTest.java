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
        mZoneDto = new ZoneDto("zona 1", 1, "ue1");
    }

    @Test
    public void getName_SameName_ReturnsTrue() {
        assertTrue(mZoneDto.getName().equals("zona 1"));
    }

    @Test
    public void setName_DifferentName_ReturnsTrue() {
        mZoneDto.setName("zona 2");
        assertTrue(mZoneDto.getName().equals("zona 2"));
    }

    @Test
    public void getIdentifier_SameIdentifier_ReturnsTrue() {
        assertTrue(mZoneDto.getIdentifier() == 1);
    }

    @Test
    public void setIdentifier_DifferentIdentifier_ReturnsTrue() {
        mZoneDto.setIdentifier(2);
        assertTrue(mZoneDto.getIdentifier() == 2);
    }

    @Test
    public void getExecUnitName_SameExecUnitName_ReturnsTrue() {
        assertTrue(mZoneDto.getExecUnitName().equals("ue1"));
    }

    @Test
    public void setExecUnitName_DifferentExecUnitName_ReturnsTrue() {
        mZoneDto.setExecUnitName("ue2");
        assertTrue(mZoneDto.getExecUnitName().equals("ue2"));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        ZoneDto zona = new ZoneDto(null, -1, null);
        assertFalse(zona.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ZoneDto zona = new ZoneDto(null, -1, null);
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(zona.equals(recurso));
    }

    @Test
    public void equals_DifferentName_ReturnsFalse() {
        ZoneDto zona1 = new ZoneDto("1", 1, "1");
        ZoneDto zona2 = new ZoneDto("2", 1, "1");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void equals_DifferentId_ReturnsFalse() {
        ZoneDto zona1 = new ZoneDto("1", 1, "1");
        ZoneDto zona2 = new ZoneDto("1", 2, "1");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void equals_DifferentExecUnitName_ReturnsFalse() {
        ZoneDto zona1 = new ZoneDto("1", 1, "1");
        ZoneDto zona2 = new ZoneDto("1", 1, "2");
        assertFalse(zona1.equals(zona2));
    }

    @Test
    public void equals_SameFields_ReturnsTrue() {
        ZoneDto zona1 = new ZoneDto("1", 1, "1");
        ZoneDto zona2 = new ZoneDto("1", 1, "1");
        assertTrue(zona1.equals(zona2));
    }
}
