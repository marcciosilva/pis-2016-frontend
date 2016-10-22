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
        // TODO mejorar.
        mAuthRequest.getPath();
    }

    @Test
    public void getBody() throws Exception {
        // TODO mejorar.
        mAuthRequest.getBody();
//        // Seteo atributos.
//        final String test_user = "test_user";
//        final String test_password = "test_password";
//        mAuthRequest.setAttributes(test_user, test_password);
//        // Chequeo si el getBody me devuelve el cuerpo que debiera.
//        JsonObject testBody = new JsonObject();
//        testBody.addProperty("password", test_user);
//        testBody.addProperty("username", test_password);
//        assertTrue(mAuthRequest.getBody().toString().equals(testBody.toString()));
    }

    @Test
    public void setAttributes() throws Exception {
        // TODO mejorar.
        mAuthRequest.setAttributes("test_user", "test_password");
    }

}