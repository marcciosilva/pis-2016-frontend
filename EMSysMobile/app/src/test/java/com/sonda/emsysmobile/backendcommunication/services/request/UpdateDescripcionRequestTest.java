package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

public class UpdateDescripcionRequestTest extends BaseMockTest {

    private UpdateDescriptionRequest<Integer> mUpdateDescriptionRequest;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mUpdateDescriptionRequest = new UpdateDescriptionRequest<>(context, EmsysResponse.class);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mUpdateDescriptionRequest.getPath(), "/eventos/actualizardescripcionrecurso");
    }

    @Test
    public void getBody() throws Exception {
        String desc = "test_descriptiron";
        int idExt = 1;
        mUpdateDescriptionRequest.setAttributes(desc, idExt);
        JsonObject jsonToCompare = new JsonObject();
        jsonToCompare.addProperty("descripcion", desc);
        jsonToCompare.addProperty("id_extension", idExt);
        JsonObject json = mUpdateDescriptionRequest.getBody();
        assertEquals(json, jsonToCompare);
    }

}
