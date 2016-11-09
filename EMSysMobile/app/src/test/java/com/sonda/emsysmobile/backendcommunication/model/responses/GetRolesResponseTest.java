package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class GetRolesResponseTest {

    private GetRolesResponse mGetRolesResponse;
    private RoleDto mRoleDto;

    @Before
    public void setUp() {
        mGetRolesResponse = new GetRolesResponse();
        mGetRolesResponse.setCode(0);
        ZoneDto mZone1 = new ZoneDto("zona 1", 1234, "ue1");
        ZoneDto mZone2 = new ZoneDto("zona 2", 8262, "ue2");
        ArrayList<ZoneDto> mZones = new ArrayList<>();
        mZones.add(mZone1);
        mZones.add(mZone2);
        ResourceDto mResource1 = new ResourceDto("A123", 1);
        ResourceDto mResource2 = new ResourceDto("B621", 2);
        ArrayList<ResourceDto> mResources = new ArrayList<>();
        mResources.add(mResource1);
        mResources.add(mResource2);
        mRoleDto = new RoleDto(mZones, mResources);
        mGetRolesResponse.setRoles(mRoleDto);
    }

    @Test
    public void getCode_CompareWithSameCode_ReturnsTrue() {
        assertTrue(mGetRolesResponse.getCode() == 0);
    }

    @Test
    public void getRoles_CompareWithSameRoles_ReturnsTrue() {
        assertTrue(mGetRolesResponse.getRoles().equals(mRoleDto));
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mGetRolesResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mGetRolesResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        GetRolesResponse testResponse = new GetRolesResponse();
        testResponse.setCode(0);
        ZoneDto testZone1 = new ZoneDto("zona 1", 1234, "ue1");
        ZoneDto testZone2 = new ZoneDto("zona 2", 8262, "ue2");
        ResourceDto testResource1 = new ResourceDto("A123", 1);
        ResourceDto testResource2 = new ResourceDto("B621", 2);
        ArrayList<ZoneDto> testZones = new ArrayList<>();
        testZones.add(testZone1);
        testZones.add(testZone2);
        ArrayList<ResourceDto> testResources = new ArrayList<>();
        testResources.add(testResource1);
        testResources.add(testResource2);
        RoleDto testRoleDto = new RoleDto(testZones, testResources);
        testResponse.setRoles(testRoleDto);
        assertTrue(mGetRolesResponse.equals(testResponse));
    }

}
