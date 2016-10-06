package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class ResourceDtoUnitTest {

    private ResourceDto mResourceDto;

    @Before
    public void setUp() {
        mResourceDto = new ResourceDto("1", 1);
    }

    @Test
    public void getId_CompareWithSameId_ReturnsTrue() {
        assertTrue(mResourceDto.getId() == 1);
    }

    @Test
    public void setId_DifferentId_ReturnsTrue() {
        mResourceDto.setId(4);
        assertTrue(mResourceDto.getId() == 4);
    }

    @Test
    public void getCode_CompareWithSameCode_ReturnsTrue() {
        assertTrue(mResourceDto.getCode().equals("1"));
    }

    @Test
    public void setCode_DifferentCode_ReturnsTrue() {
        mResourceDto.setCode("4");
        assertTrue(mResourceDto.getCode().equals("4"));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        assertFalse(resource.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        RoleDto rol = new RoleDto(null, null);
        assertFalse(resource.equals(rol));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        ResourceDto resource1 = new ResourceDto("1", 1);
        ResourceDto resource2 = new ResourceDto("1", 1);
        assertTrue(resource1.equals(resource2));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        ResourceDto resource1 = new ResourceDto("1", 1);
        ResourceDto resource2 = new ResourceDto("1", 2);
        assertFalse(resource1.equals(resource2));
    }

}
