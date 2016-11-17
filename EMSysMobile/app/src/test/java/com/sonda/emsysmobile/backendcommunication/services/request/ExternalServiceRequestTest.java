package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by marccio on 10/22/16.
 */
public class ExternalServiceRequestTest extends BaseMockTest {

    private ExternalServiceRequest<Integer> mExternalServiceRequest;

    ExternalServiceQueryDto mExternalServiceQueryDto;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mExternalServiceQueryDto = new
                ExternalServiceQueryDto("parameter_1", "parameter_2", "parameter_3");
        mExternalServiceRequest = new ExternalServiceRequest<>(context, EmsysResponse.class,
                mExternalServiceQueryDto);
    }

    @Test
    public void getPath() throws Exception {
        mExternalServiceRequest.getPath().equals(ExternalServiceRequest.CONSUME_WS_PATH);
    }

    @Test
    public void getBody() throws Exception {
        String jsonString = new Gson().toJson(mExternalServiceQueryDto);
        JsonObject jsonToCompare = (JsonObject) new JsonParser().parse(jsonString);
        JsonObject json = mExternalServiceRequest.getBody();
        assertEquals(json, jsonToCompare);
    }

}