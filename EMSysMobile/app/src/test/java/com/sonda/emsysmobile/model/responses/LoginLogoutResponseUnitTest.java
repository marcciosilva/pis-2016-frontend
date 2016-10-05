package com.sonda.emsysmobile.model.responses;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */
public class LoginLogoutResponseUnitTest {

    private ErrorResponse mInnerResponse;
    private LoginLogoutResponse mLoginLogoutResponse;

    /**
     * Ejecuta antes de cada test de la suite.
     */
    @Before
    public void executeBeforeEach() {
        mLoginLogoutResponse = new LoginLogoutResponse();
        mInnerResponse = new ErrorResponse();
        mLoginLogoutResponse.setInnerResponse(mInnerResponse);
        mInnerResponse.setMsg("errorMsg");
        mLoginLogoutResponse.setCode(0);
    }

    /**
     * Prueba get de respuesta interna.
     */
    @Test
    public void getInnerResponse_SameInnerResponse_ReturnsTrue() {
        assertTrue(mLoginLogoutResponse.getInnerResponse().equals(mInnerResponse));
    }

    /**
     * Prueba seteo de respuesta interna.
     */
    @Test
    public void setInnerResponse_Null_ReturnsTrue() {
        mLoginLogoutResponse.setInnerResponse(null);
        assertTrue(mLoginLogoutResponse.getInnerResponse() == null);
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mLoginLogoutResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mLoginLogoutResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_SameFields_ReturnsTrue() {
        LoginLogoutResponse testResponse = new LoginLogoutResponse();
        testResponse.setCode(0);
        ErrorResponse testInnerResponse = new ErrorResponse();
        testResponse.setInnerResponse(testInnerResponse);
        testInnerResponse.setMsg("errorMsg");
        assertTrue(mLoginLogoutResponse.equals(testResponse));
    }

}
