package com.sonda.emsysmobile.model.responses;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */
public class ErrorResponseTest {

    private ErrorResponse mErrorResponse;

    @Before
    public void setUp() {
        mErrorResponse = new ErrorResponse();
        mErrorResponse.setMsg("errorMsg");
    }

    /**
     * Prueba que el get del msg funcione bien.
     */
    @Test
    public void getMsg_CompareWithSameMessage_ReturnsTrue() {
        assertTrue(mErrorResponse.getMsg().equals("errorMsg"));
    }

    /**
     * Prueba que el seteo del msg se haga correctamente.
     */
    @Test
    public void setMsg_ChangeMessage_ReturnsTrue() {
        String altErrorMsg = "alt_errorMsg";
        mErrorResponse.setMsg(altErrorMsg);
        assertTrue(mErrorResponse.getMsg().equals(altErrorMsg));
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mErrorResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mErrorResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        ErrorResponse testInnerResponse = new ErrorResponse();
        testInnerResponse.setMsg("errorMsg");
        assertTrue(mErrorResponse.equals(testInnerResponse));
    }

}