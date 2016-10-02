package com.sonda.emsysmobile.model.responses;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */
public class LoginLogoutResponseUnitTest {

    private LoginLogoutInnerResponse mInnerResponse;
    private LoginLogoutResponse mLoginLogoutResponse;

    @Before
    public void executeBeforeEach() {
        mLoginLogoutResponse = new LoginLogoutResponse();
        mInnerResponse = new LoginLogoutInnerResponse();
        mLoginLogoutResponse.setInnerResponse(mInnerResponse);
        mInnerResponse.setErrorMsg("errorMsg");
        mLoginLogoutResponse.setCode(0);
    }

    @Test
    public void testGetCodigoRespuesta() {
        assertTrue(mLoginLogoutResponse.getCode() == 0);
    }

    @Test
    public void testGetErrorMessage_nonNullResponse_nonNullErrorMsg() {
        assertTrue(mLoginLogoutResponse.getErrorMessage().equals("errorMsg"));
    }

    @Test
    public void testGetErrorMessage_nonNullResponse_nullErrorMsg() {
        mLoginLogoutResponse.getInnerResponse().setErrorMsg(null);
        assertTrue(mLoginLogoutResponse.getErrorMessage().equals(""));
    }

    @Test
    public void testGetErrorMessage_null() {
        mLoginLogoutResponse.setInnerResponse(null);
        assertTrue(mLoginLogoutResponse.getErrorMessage().equals(""));
    }
}
