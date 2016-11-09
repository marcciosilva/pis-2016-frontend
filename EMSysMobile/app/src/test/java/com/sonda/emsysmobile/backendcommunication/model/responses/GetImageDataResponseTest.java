package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

public class GetImageDataResponseTest {

    private ImageDataDto mImageDataDto;

    @Before
    public void setUp(){
        mImageDataDto = new ImageDataDto("test_name", "test_data");
    }

    @Test
    public void getName(){
        assertEquals(mImageDataDto.getName(), "test_name");
    }

    @Test
    public void getData(){
        assertEquals(mImageDataDto.getData(), "test_data");
    }
}
