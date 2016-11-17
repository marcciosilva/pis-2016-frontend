package com.sonda.emsysmobile.logic.model.core.attachments;

import com.sonda.emsysmobile.logic.model.core.CategoryDto;
import com.sonda.emsysmobile.logic.model.core.CategoryPriority;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class AudioDtoTest {

    private AudioDto mAudioDto;
    private String mPath;

    @Before
    public void setUp() throws Exception {
        mPath = "base_path";
        mAudioDto = new AudioDto(mPath);
    }

    @Test
    public void getPath() throws Exception {
        assertTrue(mAudioDto.getPath().equals(mPath));
    }

    @Test
    public void setPath() throws Exception {
        String testPath = "test_path";
        mAudioDto.setPath(testPath);
        assertTrue(mAudioDto.getPath().equals(testPath));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mAudioDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(mAudioDto.equals(recurso));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        assertTrue(mAudioDto.equals(new AudioDto(mPath)));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        assertFalse(mAudioDto.equals(new AudioDto("alt_path")));
    }

}