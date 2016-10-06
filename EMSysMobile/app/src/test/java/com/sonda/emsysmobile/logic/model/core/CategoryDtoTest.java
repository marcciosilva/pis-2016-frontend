package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 10/5/16.
 */

public class CategoryDtoTest {

    private CategoryDto mCategoryDto;

    @Before
    public void setUp() {
        mCategoryDto = new CategoryDto(1, "1", "key1", CategoryPriority.HIGH, true);
    }

    @Test
    public void getIdentifier_CompareWithSameIdentifier_ReturnsTrue() {
        assertTrue(mCategoryDto.getIdentifier() == 1);
    }

    @Test
    public void setIdentifier_DifferentIdentifier_ReturnsTrue() {
        mCategoryDto.setIdentifier(2);
        assertTrue(mCategoryDto.getIdentifier() == 2);
    }

    @Test
    public void getCode_CompareWithSameCode_ReturnsTrue() {
        assertTrue(mCategoryDto.getCode().equals("1"));
    }

    @Test
    public void setCode_DifferentCode_ReturnsTrue() {
        mCategoryDto.setCode("4");
        assertTrue(mCategoryDto.getCode().equals("4"));
    }

    @Test
    public void getKey_CompareWithSameKey_ReturnsTrue() {
        assertTrue(mCategoryDto.getKey().equals("key1"));
    }

    @Test
    public void setKey_DifferentKey_ReturnsTrue() {
        mCategoryDto.setKey("alt_key");
        assertTrue(mCategoryDto.getKey().equals("alt_key"));
    }

    @Test
    public void getPriority_CompareWithSamePriority_ReturnsTrue() {
        assertTrue(mCategoryDto.getPriority() == CategoryPriority.HIGH);
    }

    @Test
    public void setPriority_DifferentPriority_ReturnsTrue() {
        mCategoryDto.setPriority(CategoryPriority.LOW);
        assertTrue(mCategoryDto.getPriority() == CategoryPriority.LOW);
    }

    @Test
    public void isActive_CompareWithSameIsActive_ReturnsTrue() {
        assertTrue(mCategoryDto.getActive());
    }

    @Test
    public void setActive_DifferentIsActive_ReturnsFalse() {
        mCategoryDto.setActive(false);
        assertFalse(mCategoryDto.getActive());
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mCategoryDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(mCategoryDto.equals(recurso));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        CategoryDto testCategoryDto = new CategoryDto(1, "1", "key1", CategoryPriority.HIGH, true);
        assertTrue(mCategoryDto.equals(testCategoryDto));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        CategoryDto testCategoryDto = new CategoryDto(1, "1", "key1", CategoryPriority.HIGH, false);
        assertFalse(mCategoryDto.equals(testCategoryDto));
    }

}
