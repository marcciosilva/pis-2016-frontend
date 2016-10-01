package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.responses.LoginInnerResponse;
import com.sonda.emsysmobile.model.responses.LoginResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 9/30/16.
 */
public class LoginInnerResponseTest {

    private LoginInnerResponse mInnerResponse;

    @Before
    public void executeBeforeEach() {
        mInnerResponse = new LoginInnerResponse();
        mInnerResponse.setErrorMsg("errorMsg");
    }

    @Test
    public void testGetErrorMsg() {
        assertTrue(mInnerResponse.getErrorMsg().equals("errorMsg"));
    }

    @Test
    public void testSetErrorMsg() {
        String altErrorMsg = "alt_errorMsg";
        mInnerResponse.setErrorMsg(altErrorMsg);
        assertTrue(mInnerResponse.getErrorMsg().equals(altErrorMsg));
    }

}