package com.sonda.emsysmobile.logic.model.core;

import com.sonda.emsysmobile.utils.DateUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 11/15/16.
 */
public class DescriptionDtoTest {

    private DescriptionDto mDescriptionDto;
    private String mUser;
    private Date mDate;
    private String mDescription;
    private int mOrigin;
    private boolean mOfflineAdded;

    @Before
    public void setUp() throws Exception {
        mUser = "user";
        mDate = new Date(1992, 1, 1);
        mDescription = "description";
        mOrigin = 1;
        mOfflineAdded = true;
        mDescriptionDto = new DescriptionDto(mUser, mDate, mDescription, mOrigin, mOfflineAdded);
    }

    @Test
    public void setUser() throws Exception {
        final String altUser = "alt_user";
        mDescriptionDto.setUser(altUser);
        assertTrue(mDescriptionDto.getUser().equals(altUser));
    }

    @Test
    public void setDate() throws Exception {
        final Date date = new Date();
        mDescriptionDto.setDate(date);
        assertTrue(mDescriptionDto.getDate().equals(date));
    }

    @Test
    public void setDescription() throws Exception {
        final String altDescription = "alt_description";
        mDescriptionDto.setDescription(altDescription);
        assertTrue(mDescriptionDto.getDescription().equals(altDescription));
    }

    @Test
    public void setOrigin() throws Exception {
        final int altOrigin = 2;
        mDescriptionDto.setOrigin(altOrigin);
        assertTrue(mDescriptionDto.getOrigin() == altOrigin);
    }

    @Test
    public void setOfflineAdded() throws Exception {
        mDescriptionDto.setOfflineAdded(!mOfflineAdded);
        assertTrue(mDescriptionDto.isOfflineAdded() == !mOfflineAdded);
    }

    @Test
    public void toStringTest() throws Exception {
        final String expectedString = "[" + DateUtils.dateToString(mDescriptionDto
                .getDate()) + " - " +
                mDescriptionDto.getUser() + "] [OFFLINE] " + mDescriptionDto.getDescription();
        assertTrue(mDescriptionDto.toString().equals(expectedString));
    }

}