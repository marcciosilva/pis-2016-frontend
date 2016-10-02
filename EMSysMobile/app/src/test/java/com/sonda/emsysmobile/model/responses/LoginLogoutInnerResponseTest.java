package com.sonda.emsysmobile.model.responses;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 9/30/16.
 */
public class LoginLogoutInnerResponseTest {

    private LoginLogoutInnerResponse mInnerResponse;

    @Before
    public void executeBeforeEach() {
        mInnerResponse = new LoginLogoutInnerResponse();
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