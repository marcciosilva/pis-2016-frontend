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

public class EventsRequestTest extends BaseMockTest {

    private EventsRequest<Integer> mEventRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mEventRequest = new EventsRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mEventRequest.getPath(), EventsRequest.EVENTS_PATH);
    }

    @Test
    public void getBody() throws Exception {
        assertTrue(mEventRequest.getBody() == null);
    }
}
