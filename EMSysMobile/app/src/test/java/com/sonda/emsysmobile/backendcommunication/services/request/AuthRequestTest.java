package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class AuthRequestTest extends BaseMockTest {

    private AuthRequest<Integer> mAuthRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mAuthRequest = new AuthRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mAuthRequest.getPath(), AuthRequest.AUTH_PATH);
    }

    @Test
    public void getBody() throws Exception {
        // Seteo atributos.
        final String test_user = "test_user";
        final String test_password = "test_password";
        mAuthRequest.setAttributes(test_user, test_password);
        JsonObject body = mAuthRequest.getBody();
        assertEquals(body.get("username").getAsString(), test_user);
        assertEquals(body.get("password").getAsString(), test_password);
    }

    @Test
    public void setAttributes() throws Exception {
        mAuthRequest.setAttributes("test_user", "test_password");
    }

}