package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

public class UpdateGeoLocationRequestTest extends BaseMockTest {

    private UpdateGeoLocationRequest<Integer> mUpdateGeoLocationRequest;
    private GeolocationDto mGeoLocationDto;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mGeoLocationDto = new GeolocationDto(1, "test_user", new Date(), 1.1, 1.1);
        mUpdateGeoLocationRequest = new UpdateGeoLocationRequest<>(context, EmsysResponse.class, mGeoLocationDto);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mUpdateGeoLocationRequest.getPath(), UpdateGeoLocationRequest.UPDATEGEOLOCATION_PATH);
    }

    @Test
    public void getBody() throws Exception {
        String jsonString = new Gson().toJson(mGeoLocationDto);
        JsonObject jsonToCompare = (JsonObject) new JsonParser().parse(jsonString);
        JsonObject json = mUpdateGeoLocationRequest.getBody();
        assertEquals(json, jsonToCompare);
    }
}
