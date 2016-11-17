package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class EventDetailsRequestTest extends BaseMockTest {

    private EventDetailsRequest<Integer> mEventDetailsRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mEventDetailsRequest = new EventDetailsRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        final int eventId = 1;
        mEventDetailsRequest.setAttributes(eventId);
        assertTrue(mEventDetailsRequest.getPath().equals(EventDetailsRequest.EVENT_DETAILS_PATH + "?idEvento=" +
                Integer.toString(eventId)));
    }

    @Test
    public void getBody() throws Exception {
        assertTrue(mEventDetailsRequest.getBody() == null);
    }

    @Test
    public void setAttributes() throws Exception {
        final int eventId = 2;
        mEventDetailsRequest.setAttributes(eventId);
        assertTrue(mEventDetailsRequest.getPath().equals(EventDetailsRequest.EVENT_DETAILS_PATH + "?idEvento=" +
                Integer.toString(eventId)));
    }

}