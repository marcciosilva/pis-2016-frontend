package com.sonda.emsysmobile.backendcommunication.services.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.UserDto;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pape on 9/11/2016.
 */

public class OfflineUpdateDescriptionRequestTest extends BaseMockTest{

    private OfflineUpdateDescriptionRequest<Integer> mOfflineUpdateDescriptionRequest;
    private OfflineAttachDescriptionDto mOfflineAttachDescriptionDto;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mOfflineAttachDescriptionDto = new OfflineAttachDescriptionDto();
        mOfflineAttachDescriptionDto.setDescription("descripcion_test");
        mOfflineAttachDescriptionDto.setExtensionId(1);
        mOfflineAttachDescriptionDto.setTimeStamp(new Date());
        UserDto mUserDto = new UserDto();
        mOfflineAttachDescriptionDto.setUserData(mUserDto);

        mOfflineUpdateDescriptionRequest = new OfflineUpdateDescriptionRequest<>(context, EmsysResponse.class, mOfflineAttachDescriptionDto);
    }

    @Test
    public void getPath() throws Exception {
        assertEquals(mOfflineUpdateDescriptionRequest.getPath(), OfflineUpdateDescriptionRequest.OFFLINE_ATTACH_DESC_PATH);
    }

    @Test
    public void getBody() throws Exception {
        String jsonString = new Gson().toJson(mOfflineAttachDescriptionDto);
        JsonObject jsonToCompare = (JsonObject) new JsonParser().parse(jsonString);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        jsonToCompare.addProperty("time_stamp", simpleDateFormat
                .format(mOfflineAttachDescriptionDto.getTimeStamp()));
        JsonObject json = mOfflineUpdateDescriptionRequest.getBody();
        assertEquals(json, jsonToCompare);
    }

}
