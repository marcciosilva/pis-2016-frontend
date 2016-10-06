package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class RoleDtoUnitTest {

    private RoleDto mRoleDto;
    private ArrayList<ZoneDto> mZones;
    List<ResourceDto> mResources;

    @Before
    public void setUp() {
        ZoneDto testZone1 = new ZoneDto("zone 1", 1234, "ue1");
        ZoneDto testZone2 = new ZoneDto("zone 2", 8262, "ue2");
        ResourceDto testResource1 = new ResourceDto("A123", 1);
        ResourceDto testResource2 = new ResourceDto("B621", 1);
        mZones = new ArrayList<>();
        mZones.add(testZone1);
        mZones.add(testZone2);
        mResources = new ArrayList<>();
        mResources.add(testResource1);
        mResources.add(testResource2);
        mRoleDto = new RoleDto(mZones, mResources);
    }

    @Test
    public void getZones_CompareWithSameZones_ReturnsTrue() {
        List<ZoneDto> zones = mRoleDto.getZones();
        assertTrue(zones.equals(mZones));
    }

    @Test
    public void getResources_CompareWithSameResources_ReturnsTrue() {
        List<ResourceDto> resources = mRoleDto.getResources();
        assertTrue(resources.equals(mResources));
    }

    @Test
    public void setZones_DifferentZones_ReturnsTrue() {
        mZones.add(new ZoneDto("zone 3", 5312, "ue1"));
        mRoleDto.setZones(mZones);
        assertTrue(mRoleDto.getZones().equals(mZones));
    }

    @Test
    public void setResources_DifferentResource_ReturnsTrue() {
        mResources.add(new ResourceDto("9875", 1));
        mRoleDto.setResources(mResources);
        assertTrue(mRoleDto.getResources().equals(mResources));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mRoleDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        assertFalse(mRoleDto.equals(resource));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        ZoneDto testZone1 = new ZoneDto("zone 1", 1234, "ue1");
        ZoneDto testZone2 = new ZoneDto("zone 2", 8262, "ue2");
        ResourceDto testResource1 = new ResourceDto("A123", 1);
        ResourceDto testResource2 = new ResourceDto("B621", 1);
        ArrayList<ZoneDto> testZones = new ArrayList<>();
        testZones.add(testZone1);
        testZones.add(testZone2);
        ArrayList<ResourceDto> testResources = new ArrayList<>();
        testResources.add(testResource1);
        testResources.add(testResource2);
        RoleDto testRoleDto = new RoleDto(testZones, testResources);
        assertTrue(mRoleDto.equals(testRoleDto));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        ZoneDto testZone1 = new ZoneDto("zone 1", 1239, "ue1");
        ZoneDto testZone2 = new ZoneDto("zone 2", 8262, "ue2");
        ResourceDto testResource1 = new ResourceDto("A123", 1);
        ResourceDto testResource2 = new ResourceDto("B621", 2);
        ArrayList<ZoneDto> testZones = new ArrayList<>();
        testZones.add(testZone1);
        testZones.add(testZone2);
        ArrayList<ResourceDto> testResources = new ArrayList<>();
        testResources.add(testResource1);
        testResources.add(testResource2);
        RoleDto testRoleDto = new RoleDto(testZones, testResources);
        assertFalse(mRoleDto.equals(testRoleDto));
    }

}
