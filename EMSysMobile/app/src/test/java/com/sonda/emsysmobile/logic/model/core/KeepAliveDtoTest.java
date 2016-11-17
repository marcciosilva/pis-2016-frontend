package com.sonda.emsysmobile.logic.model.core;

import com.sonda.emsysmobile.backendcommunication.model.responses.KeepAliveResponse;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class KeepAliveDtoTest {

    private KeepAliveDto mKeepAliveDto;

    @Before
    public void setUp() throws Exception {
        mKeepAliveDto = new KeepAliveDto(1);
    }

    @Test
    public void getExpirationTime() throws Exception {
        assertTrue(mKeepAliveDto.getExpirationTime() == 1);
    }

    @Test
    public void setExpirationTime() throws Exception {
        mKeepAliveDto.setExpirationTime(2);
        assertTrue(mKeepAliveDto.getExpirationTime() == 2);
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mKeepAliveDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        assertFalse(mKeepAliveDto.equals(resource));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        assertTrue(mKeepAliveDto.equals(new KeepAliveDto(1)));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        assertFalse(mKeepAliveDto.equals(new KeepAliveDto(2)));
    }

}