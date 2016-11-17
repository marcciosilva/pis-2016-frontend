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

public class AttachImageRequestTest extends BaseMockTest {

    private AttachImageRequest<Integer> mAttachImageRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mAttachImageRequest = new AttachImageRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mAttachImageRequest.getPath(), AttachImageRequest.UPLOAD_IMAGE_PATH);
    }

    @Test
    public void getBody() throws Exception {
        JsonObject jsonToCompare = new JsonObject();
        jsonToCompare.addProperty("nombre", "test_name");
        jsonToCompare.addProperty("file_data", "test_image");
        jsonToCompare.addProperty("id_extension", 1);
        mAttachImageRequest.setAttributes(1, "test_name", "test_image");
        JsonObject json = mAttachImageRequest.getBody();
        assertEquals(json, jsonToCompare);
    }

    @Test
    public void setAttributes() throws Exception{
        mAttachImageRequest.setAttributes(1, "test_name", "test_image");
    }

}
