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
    private String mZone1String = "zone 1";
    private String mZone2String = "zone 2";

    @Before
    public void setUp() {
        mZoneDto = new ZoneDto(mZone1String, 1, "ue1");
    }

    @Test
    public void getName_CompareWithSameName_ReturnsTrue() {
        assertTrue(mZoneDto.getName().equals(mZone1String));
    }

    @Test
    public void setName_DifferentName_ReturnsTrue() {
        mZoneDto.setName(mZone2String);
        assertTrue(mZoneDto.getName().equals(mZone2String));
    }

    @Test
    public void getIdentifier_CompareWithSameIdentifier_ReturnsTrue() {
        assertTrue(mZoneDto.getIdentifier() == 1);
    }

    @Test
    public void setIdentifier_DifferentIdentifier_ReturnsTrue() {
        mZoneDto.setIdentifier(2);
        assertTrue(mZoneDto.getIdentifier() == 2);
    }

    @Test
    public void getExecUnitName_CompareWithSameExecUnitName_ReturnsTrue() {
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
    public void equals_CompareWithSameFields_ReturnsTrue() {
        ZoneDto zona1 = new ZoneDto("1", 1, "1");
        ZoneDto zona2 = new ZoneDto("1", 1, "1");
        assertTrue(zona1.equals(zona2));
    }
}
