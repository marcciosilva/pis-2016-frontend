package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.core.ResourceDto;
import com.sonda.emsysmobile.model.core.RoleDto;
import com.sonda.emsysmobile.model.core.ZoneDto;
import com.sonda.emsysmobile.model.responses.AuthInnerResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by marccio on 9/30/16.
 */
public class AuthInnerResponseTest {

    private AuthInnerResponse mAuthInnerResponse;

    @Before
    public void setUp() {
        mAuthInnerResponse = new AuthInnerResponse();
        mAuthInnerResponse.setAccessToken("token");
        mAuthInnerResponse.setExpirationTime(1);
    }

    @Test
    public void getAccessToken_CompareWithSameAccessToken_ReturnsTrue() {
        assertTrue(mAuthInnerResponse.getAccessToken().equals("token"));
    }

    @Test
    public void setAccessToken_DifferentAccessToken_ReturnsTrue() {
        String altToken = "alt_token";
        mAuthInnerResponse.setAccessToken(altToken);
        assertTrue(mAuthInnerResponse.getAccessToken().equals(altToken));
    }

    @Test
    public void getExpirationTime_CompareWithSameAccessToken_ReturnsTrue() {
        assertTrue(mAuthInnerResponse.getExpirationTime() == 1);
    }

    @Test
    public void setExpirationTime_DifferentAccessToken_ReturnsTrue() {
        mAuthInnerResponse.setExpirationTime(2);
        assertTrue(mAuthInnerResponse.getExpirationTime() == 2);
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mAuthInnerResponse.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        assertFalse(mAuthInnerResponse.equals(resource));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        AuthInnerResponse testAuthInnerResponse = new AuthInnerResponse();
        testAuthInnerResponse.setAccessToken("token");
        testAuthInnerResponse.setExpirationTime(1);
        assertTrue(mAuthInnerResponse.equals(testAuthInnerResponse));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        AuthInnerResponse testAuthInnerResponse = new AuthInnerResponse();
        testAuthInnerResponse.setAccessToken("token");
        testAuthInnerResponse.setExpirationTime(2);
        assertFalse(mAuthInnerResponse.equals(testAuthInnerResponse));
    }



}