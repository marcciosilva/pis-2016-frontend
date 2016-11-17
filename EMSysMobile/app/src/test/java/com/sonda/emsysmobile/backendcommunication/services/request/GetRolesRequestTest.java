package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class GetRolesRequestTest extends BaseMockTest {

    private GetRolesRequest<Integer> mGetRolesRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mGetRolesRequest = new GetRolesRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mGetRolesRequest.getPath(), GetRolesRequest.GET_ROLES_PATH);
    }

    @Test
    public void getBody() throws Exception {
        assertTrue(mGetRolesRequest.getBody() == null);
    }

}