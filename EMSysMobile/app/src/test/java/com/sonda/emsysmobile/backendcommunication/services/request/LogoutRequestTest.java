package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class LogoutRequestTest extends BaseMockTest {

    private LogoutRequest<Integer> mLogoutRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mLogoutRequest = new LogoutRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertTrue(mLogoutRequest.getPath().equals(LogoutRequest.LOGOUT_PATH));
    }

    @Test
    public void getBody() throws Exception {
        assertTrue(mLogoutRequest.getBody() == null);
    }

}