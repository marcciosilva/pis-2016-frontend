package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.responses.AuthInnerResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 9/30/16.
 */
public class AuthInnerResponseTest {

    private AuthInnerResponse mAuthInnerResponse;

    @Before
    public void executeBeforeEach() {
        mAuthInnerResponse = new AuthInnerResponse();
        mAuthInnerResponse.setAccessToken("token");
        mAuthInnerResponse.setExpirationTime(1);
    }

    @Test
    public void testGetAccessToken() {
        assertTrue(mAuthInnerResponse.getAccessToken().equals("token"));
    }

    @Test
    public void testSetAccessToken() {
        String altToken = "alt_token";
        mAuthInnerResponse.setAccessToken(altToken);
        assertTrue(mAuthInnerResponse.getAccessToken().equals(altToken));
    }

    @Test
    public void testGetExpirationTime() {
        assertTrue(mAuthInnerResponse.getExpirationTime() == 1);
    }

    @Test
    public void testSetExpirationTime() {
        mAuthInnerResponse.setExpirationTime(2);
        assertTrue(mAuthInnerResponse.getExpirationTime() == 2);
    }

}