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
    private ErrorResponse mInnerResponse;

    @Before
    public void setUp() {
        mKeepAliveResponse = new KeepAliveResponse();
        mInnerResponse = new ErrorResponse();
        mInnerResponse.setMsg("errorMsg");
        mKeepAliveResponse.setInnerResponse(mInnerResponse);
        mKeepAliveResponse.setCode(0);
    }

    /**
     * Prueba get de respuesta interna.
     */
    @Test
    public void getInnerResponse_CompareWithSameInnerResponse_ReturnsTrue() {
        assertTrue(mKeepAliveResponse.getInnerResponse().equals(mInnerResponse));
    }

    /**
     * Prueba seteo de respuesta interna.
     */
    @Test
    public void setInnerResponse_Null_ReturnsTrue() {
        mKeepAliveResponse.setInnerResponse(null);
        assertTrue(mKeepAliveResponse.getInnerResponse() == null);
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mKeepAliveResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mKeepAliveResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        KeepAliveResponse testResponse = new KeepAliveResponse();
        testResponse.setCode(0);
        ErrorResponse testInnerResponse = new ErrorResponse();
        testInnerResponse.setMsg("errorMsg");
        testResponse.setInnerResponse(testInnerResponse);
        assertTrue(mKeepAliveResponse.equals(testResponse));
    }

}
