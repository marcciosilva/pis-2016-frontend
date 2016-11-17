package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class KeepAliveRequestTest extends BaseMockTest {

    private KeepAliveRequest<Integer> mKeepAliveRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mKeepAliveRequest = new KeepAliveRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertTrue(mKeepAliveRequest.getPath().equals(KeepAliveRequest.KEEP_ALIVE_PATH));
    }

    @Test
    public void getBody() throws Exception {
        assertTrue(mKeepAliveRequest.getBody() == null);
    }

}