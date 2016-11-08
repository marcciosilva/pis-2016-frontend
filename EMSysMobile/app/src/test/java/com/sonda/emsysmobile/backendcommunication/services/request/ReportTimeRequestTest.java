package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pape on 8/11/2016.
 */

public class ReportTimeRequestTest extends BaseMockTest {

    private ReportTimeRequest<Integer> mReportTimeRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mReportTimeRequest = new ReportTimeRequest<>(context, EmsysResponse.class, 1);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mReportTimeRequest.getPath(), ReportTimeRequest.REPORTTIME_PATH);
    }

    @Test
    public void getBody() throws Exception {
        JsonObject jsonToCompare = new JsonObject();
        jsonToCompare.addProperty("idExtension", 1);
        JsonObject json = mReportTimeRequest.getBody();
        assertEquals(json, jsonToCompare);
    }
}
