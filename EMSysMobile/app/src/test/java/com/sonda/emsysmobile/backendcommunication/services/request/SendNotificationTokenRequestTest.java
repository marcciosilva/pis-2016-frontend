package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pape on 9/11/2016.
 */

public class SendNotificationTokenRequestTest extends BaseMockTest{

    private SendNotificationTokenRequest<Integer> mSendNotificationRequest;
    private String mToken;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mToken = "test_token";
        mSendNotificationRequest = new SendNotificationTokenRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mSendNotificationRequest.getPath(), "/users/SetRegistrationToken");
    }

    @Test
    public void setToken() throws  Exception{
        mSendNotificationRequest.setToken(mToken);
    }

    @Test
    public void getBody() throws Exception {
        mSendNotificationRequest.setToken(mToken);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("registrationTokens", mToken);
        JsonObject json = mSendNotificationRequest.getBody();
        assertEquals(jsonObject, json);
    }

}
