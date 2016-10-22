package com.sonda.emsysmobile.logic.model.core.attachments;

import com.sonda.emsysmobile.logic.model.core.ResourceDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class ImageDtoTest {

    private ImageDto mImageDto;
    private String mPath;

    @Before
    public void setUp() throws Exception {
        mPath = "base_path";
        mImageDto = new ImageDto(mPath);
    }

    @Test
    public void getPath() throws Exception {
        assertTrue(mImageDto.getPath().equals(mPath));
    }

    @Test
    public void setPath() throws Exception {
        String testPath = "test_path";
        mImageDto.setPath(testPath);
        assertTrue(mImageDto.getPath().equals(testPath));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mImageDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(mImageDto.equals(recurso));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        assertTrue(mImageDto.equals(new ImageDto(mPath)));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        assertFalse(mImageDto.equals(new ImageDto("alt_path")));
    }

}