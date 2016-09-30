package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.responses.LoginInnerResponse;
import com.sonda.emsysmobile.model.responses.LoginResponse;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class LoginResponseUnitTest {

    private LoginInnerResponse mInnerResponse;
    private LoginResponse mLoginResponse;

    @Before
    public void executeBeforeEach() {
        mLoginResponse = new LoginResponse();
        mInnerResponse = new LoginInnerResponse();
        mLoginResponse.setResponse(mInnerResponse);
        mInnerResponse.setErrorMsg("errorMsg");
        mLoginResponse.setCodigoRespuesta("0");
    }

    @Test
    public void testGetCodigoRespuesta() {
        assertTrue(mLoginResponse.getCodigoRespuesta().equals("0"));
    }

    @Test
    public void testGetErrorMessage_nonNullResponse_nonNullErrorMsg() {
        assertTrue(mLoginResponse.getErrorMessage().equals("errorMsg"));
    }

    @Test
    public void testGetErrorMessage_nonNullResponse_nullErrorMsg() {
        mLoginResponse.getInnerResponse().setErrorMsg(null);
        assertTrue(mLoginResponse.getErrorMessage().equals(""));
    }

    @Test
    public void testGetErrorMessage_null() {
        mLoginResponse.setResponse(null);
        assertTrue(mLoginResponse.getErrorMessage().equals(""));
    }

}
