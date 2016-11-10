package com.sonda.emsysmobile.logic.model.core.attachments;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 08-Nov-16.
 */
public class ImageDataDtoTest {

    private ImageDataDto imageDataDto;
    private String mName;
    private String mData;

    @Before
    public void setUp() throws Exception {
        imageDataDto = new ImageDataDto("name", "data");
    }

    @Test
    public void setName() throws Exception {
        final String alt_name = "alt_name";
        imageDataDto.setName(alt_name);
        assertTrue(imageDataDto.getName().equals(alt_name));
    }

    @Test
    public void setData() throws Exception {
        final String alt_data = "alt_data";
        imageDataDto.setData(alt_data);
        assertTrue(imageDataDto.getData().equals(alt_data));
    }

}