package com.sonda.emsysmobile.logic.model.core.attachments;

import com.sonda.emsysmobile.logic.model.core.ResourceDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 10/22/16.
 */
public class VideoDtoTest {
    private VideoDto mVideoDto;
    private String mPath;

    @Before
    public void setUp() throws Exception {
        mPath = "base_path";
        mVideoDto = new VideoDto(mPath);
    }

    @Test
    public void getPath() throws Exception {
        assertTrue(mVideoDto.getPath().equals(mPath));
    }

    @Test
    public void setPath() throws Exception {
        String testPath = "test_path";
        mVideoDto.setPath(testPath);
        assertTrue(mVideoDto.getPath().equals(testPath));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mVideoDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(mVideoDto.equals(recurso));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        assertTrue(mVideoDto.equals(new VideoDto(mPath)));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        assertFalse(mVideoDto.equals(new VideoDto("alt_path")));
    }

}