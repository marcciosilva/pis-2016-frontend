package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.responses.AuthInnerResponse;
import com.sonda.emsysmobile.model.responses.AuthResponse;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class AuthResponseUnitTest {

    private AuthInnerResponse mInnerResponse;
    private AuthResponse mAuthResponse;

    @Before
    public void setUp() {
        mAuthResponse = new AuthResponse();
        mInnerResponse = new AuthInnerResponse();
        mAuthResponse.setInnerResponse(mInnerResponse);
        mInnerResponse.setAccessToken("token");
        mInnerResponse.setExpirationTime(1);
        mAuthResponse.setCode(0);
    }

    @Test
    public void getResponse_CompareWithSameFields_ReturnsTrue() {
        assertTrue(mAuthResponse.getInnerResponse().equals(mInnerResponse));
    }

    @Test
    public void setResponse_DifferentFields_ReturnsTrue() {
        AuthInnerResponse testInnerResponse = new AuthInnerResponse();
        testInnerResponse.setAccessToken("alt_token");
        testInnerResponse.setExpirationTime(1);
        mAuthResponse.setInnerResponse(testInnerResponse);
        assertTrue(mAuthResponse.getInnerResponse().equals(testInnerResponse));
    }

    @Test
    public void getCode_CompareWithSameCode_ReturnsTrue() {
        assertTrue(mAuthResponse.getCode() == 0);
    }

    @Test
    public void getAccessToken_NewResponseEmptyToken_ReturnsTrue() {
        assertTrue((new AuthResponse()).getAccessToken().equals(""));
    }

    @Test
    public void getAccessToken_CompareWithSameAccessToken_ReturnsTrue() {
        assertTrue(mAuthResponse.getAccessToken().equals("token"));
    }

    @Test
    public void getExpirationTime_NewResponse_ReturnsTrue() {
        assertTrue((new AuthResponse()).getExpirationTime() == -1);
    }

    @Test
    public void getExpirationTime_CompareWithSameExpirationTime_ReturnsTrue() {
        assertTrue(mAuthResponse.getExpirationTime() == 1);
    }
}
