package com.sonda.emsysmobile.model.core;

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
    public void executeBeforeEach() {
        mResourceDto = new ResourceDto("1", 1);
    }

    @Test
    public void getId_SameId_ReturnsTrue() {
        assertTrue(mResourceDto.getId() == 1);
    }

    @Test
    public void setId_DifferentId_ReturnsTrue() {
        mResourceDto.setId(4);
        assertTrue(mResourceDto.getId() == 4);
    }

    @Test
    public void getCode_SameCode_ReturnsTrue() {
        assertTrue(mResourceDto.getCode().equals("1"));
    }

    @Test
    public void setCode_DifferentCode_ReturnsTrue() {
        mResourceDto.setCode("4");
        assertTrue(mResourceDto.getCode().equals("4"));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(recurso.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        RoleDto rol = new RoleDto(null, null);
        assertFalse(recurso.equals(rol));
    }

    @Test
    public void equals_SameFields_ReturnsTrue() {
        ResourceDto recurso1 = new ResourceDto("1", 1);
        ResourceDto recurso2 = new ResourceDto("1", 1);
        assertTrue(recurso1.equals(recurso2));
    }

}
