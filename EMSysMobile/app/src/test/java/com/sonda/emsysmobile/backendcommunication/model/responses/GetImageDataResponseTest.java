package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

public class GetImageDataResponseTest {

    private GetImageDataResponse mGetImageDataResponse;

    @Before
    public void setUp(){
        ImageDataDto mImageDataDto = new ImageDataDto("test_name", "test_data");
        mGetImageDataResponse = new GetImageDataResponse(mImageDataDto);
    }

    @Test
    public void getImageData(){
        ImageDataDto mImageDataDtoToCompare = new ImageDataDto("test_name", "test_data");
        ImageDataDto mImageDataDtoToTest = mGetImageDataResponse.getImageData();
        assertEquals(mImageDataDtoToCompare.getName(), mImageDataDtoToTest.getName());
        assertEquals(mImageDataDtoToCompare.getData(), mImageDataDtoToTest.getData());
    }
}
