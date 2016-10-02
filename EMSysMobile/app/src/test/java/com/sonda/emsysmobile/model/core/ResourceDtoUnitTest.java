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
    public void testGetCodigo() {
        assertTrue(mResourceDto.getCode().equals("1"));
    }

    @Test
    public void testSetCodigo() {
        mResourceDto.setCode("4");
        assertTrue(mResourceDto.getCode().equals("4"));
    }

    @Test
    public void testEquals_null() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(recurso.equals(null));
    }

    @Test
    public void testEquals_differentClass() {
        ResourceDto recurso = new ResourceDto(null, 0);
        RoleDto rol = new RoleDto(null, null);
        assertFalse(recurso.equals(rol));
    }

    @Test
    public void testEquals_differentCodigo() {
        ResourceDto recurso1 = new ResourceDto("1", 1);
        ResourceDto recurso2 = new ResourceDto("2", 2);
        assertFalse(recurso1.equals(recurso2));
    }

    @Test
    public void testEquals_equals() {
        ResourceDto recurso1 = new ResourceDto("1", 1);
        ResourceDto recurso2 = new ResourceDto("1", 1);
        assertTrue(recurso1.equals(recurso2));
    }

}
