package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javassist.runtime.Desc;

import static org.junit.Assert.*;

/**
 * Created by marccio on 11/15/16.
 */
public class ResourceAssignationDtoTest {

    private ResourceAssignationDto mResourceAssignationDto;
    int mId;
    String mResource;

    @Before
    public void setUp() throws Exception {
        mId = 1;
        mResource = "resource";
        mResourceAssignationDto = new ResourceAssignationDto(mId, mResource, null);
    }

    @Test
    public void setId() throws Exception {
        final int i = 2;
        mResourceAssignationDto.setId(i);
        assertTrue(mResourceAssignationDto.getId() == i);
    }

    @Test
    public void getResource() throws Exception {

    }

    @Test
    public void setResource() throws Exception {
        final String altResource = "alt_resource";
        mResourceAssignationDto.setResource(altResource);
        assertTrue(mResourceAssignationDto.getResource().equals(altResource));
    }

    @Test
    public void setDescriptions() throws Exception {
        final ArrayList<DescriptionDto> descriptions = new ArrayList<>();
        mResourceAssignationDto.setDescriptions(descriptions);
        assertTrue(mResourceAssignationDto.getDescriptions().equals(descriptions));
    }

}