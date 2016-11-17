package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pape on 8/11/2016.
 */

public class GetImageDataRequestTest extends BaseMockTest {

    private GetImageDataRequest<Integer> mGetImageDataRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mGetImageDataRequest = new GetImageDataRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        mGetImageDataRequest.setAttributes(1);
        assertEquals(mGetImageDataRequest.getPath(), GetImageDataRequest.EVENT_DETAILS_PATH + "?idImagen=1");
    }

    @Test
    public void getBody() throws Exception {
        assertTrue(mGetImageDataRequest.getBody() == null);
    }

    @Test
    public void setAttributes() throws Exception {
        mGetImageDataRequest.setAttributes(1);
    }
}
