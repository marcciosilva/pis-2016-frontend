package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/20/16.
 */
public class KeepAliveResponseTest {

    private KeepAliveResponse mKeepAliveResponse;

    @Before
    public void setUp() throws Exception {
        mKeepAliveResponse = new KeepAliveResponse(1);
    }

    @Test
    public void getExpirationTime() throws Exception {
        assertTrue(mKeepAliveResponse.getExpirationTime() == 1);
    }

    @Test
    public void setExpirationTime() throws Exception {
        mKeepAliveResponse.setExpirationTime(-1);
        assertTrue(mKeepAliveResponse.getExpirationTime() == -1);
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() throws Exception {
        assertFalse(mKeepAliveResponse.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() throws Exception {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(mKeepAliveResponse.equals(recurso));
    }

    @Test
    public void equals_DifferentExpirationTime_ReturnsFalse() throws Exception {
        KeepAliveResponse testKeepAliveResponse = new KeepAliveResponse(-1);
        assertFalse(mKeepAliveResponse.equals(testKeepAliveResponse));
    }

}