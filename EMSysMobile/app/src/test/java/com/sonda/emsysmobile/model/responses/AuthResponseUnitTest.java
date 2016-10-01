package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.responses.AuthInnerResponse;
import com.sonda.emsysmobile.model.responses.AuthResponse;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class AuthResponseUnitTest {

    private AuthInnerResponse mInnerResponse;
    private AuthResponse mAuthResponse;

    @Before
    public void executeBeforeEach() {
        mAuthResponse = new AuthResponse();
        mInnerResponse = new AuthInnerResponse();
        mAuthResponse.setInnerResponse(mInnerResponse);
        mInnerResponse.setAccessToken("token");
        mInnerResponse.setExpirationTime(1);
        mAuthResponse.setCode(0);
    }

    @Test
    public void testGetResponse() {
        assertTrue(mAuthResponse.getInnerResponse().equals(mInnerResponse));
    }

    @Test
    public void testGetCodigoRespuesta() {
        assertTrue(mAuthResponse.getCode() == 0);
    }

    @Test
    public void testGetAccessToken_null() {
        assertTrue((new AuthResponse()).getAccessToken().equals(""));
    }

    @Test
    public void testGetAccessToken_nonNull() {
        assertTrue(mAuthResponse.getAccessToken().equals("token"));
    }

    @Test
    public void testGetExpirationTime_null() {
        // Se prueba getExpirationTime con una authResponse con
        // custom response nula.
        assertTrue((new AuthResponse()).getExpirationTime() == -1);
    }

    @Test
    public void testGetExpirationTime_nonNull() {
        assertTrue(mAuthResponse.getExpirationTime() == 1);
    }
}
