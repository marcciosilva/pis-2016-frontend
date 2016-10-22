package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.KeepAliveDto;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/20/16.
 */
public class KeepAliveResponseTest {

    private KeepAliveResponse mKeepAliveResponse;
    private KeepAliveDto mKeepAliveDto;

    @Before
    public void setUp() throws Exception {
        mKeepAliveDto = new KeepAliveDto(1);
        mKeepAliveResponse = new KeepAliveResponse(mKeepAliveDto);
    }

    @Test
    public void getKeepAlive() throws Exception {
        assertTrue(mKeepAliveResponse.getKeepAlive().equals(mKeepAliveDto));
    }

    @Test
    public void setKeepAlive() throws Exception {
        KeepAliveDto testKeepAliveDto = new KeepAliveDto(2);
        mKeepAliveResponse.setKeepAlive(testKeepAliveDto);
        assertTrue(mKeepAliveResponse.getKeepAlive().equals(testKeepAliveDto));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mKeepAliveResponse.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        assertFalse(mKeepAliveResponse.equals(resource));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        assertTrue(mKeepAliveResponse.equals(new KeepAliveResponse(new KeepAliveDto(1))));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        assertFalse(mKeepAliveResponse.equals(new KeepAliveResponse(new KeepAliveDto(2))));
    }

}