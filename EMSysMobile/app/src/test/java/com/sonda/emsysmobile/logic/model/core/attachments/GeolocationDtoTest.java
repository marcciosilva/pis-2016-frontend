package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class GeolocationDtoTest {

    private GeolocationDto mGeolocationDto;
    private int mExtensionIdentifier;
    private String mUser;
    private Date mCreatedDate;
    private double mLatitude;
    private double mLongitude;

    @Before
    public void setUp() throws Exception {
        mExtensionIdentifier = 1;
        mUser = "base_user";
        mCreatedDate = new Date();
        mLatitude = 0.0;
        mLongitude = 0.0;
        mGeolocationDto = new GeolocationDto(mExtensionIdentifier, mUser, mCreatedDate, mLatitude,
                mLongitude);
    }

    @Test
    public void getExtensionIdentifier() throws Exception {
        assertTrue(mGeolocationDto.getExtensionIdentifier() == mExtensionIdentifier);
    }

    @Test
    public void setExtensionIdentifier() throws Exception {
        int testExtensionIdentifier = 2;
        mGeolocationDto.setExtensionIdentifier(testExtensionIdentifier);
        assertTrue(mGeolocationDto.getExtensionIdentifier() == testExtensionIdentifier);
    }

    @Test
    public void getUser() throws Exception {
        assertTrue(mGeolocationDto.getUser().equals(mUser));
    }

    @Test
    public void setUser() throws Exception {
        String testUser = "test_user";
        mGeolocationDto.setUser(testUser);
        assertTrue(mGeolocationDto.getUser().equals(testUser));
    }

    @Test
    public void getCreatedDate() throws Exception {
        assertTrue(mGeolocationDto.getCreatedDate().equals(mCreatedDate));
    }

    @Test
    public void setCreatedDate() throws Exception {
        Date testDate = new Date(1992, 1, 1);
        mGeolocationDto.setCreatedDate(testDate);
        assertTrue(mGeolocationDto.getCreatedDate().equals(testDate));
    }

    @Test
    public void getLatitude() throws Exception {
        assertTrue(mGeolocationDto.getLatitude() == mLatitude);
    }

    @Test
    public void setLatitude() throws Exception {
        double testLatitude = 1.0;
        mGeolocationDto.setLatitude(testLatitude);
        assertTrue(mGeolocationDto.getLatitude() == testLatitude);
    }

    @Test
    public void getLongitude() throws Exception {
        assertTrue(mGeolocationDto.getLongitude() == mLongitude);
    }

    @Test
    public void setLongitude() throws Exception {
        double testLongitude = 1.0;
        mGeolocationDto.setLongitude(testLongitude);
        assertTrue(mGeolocationDto.getLongitude() == testLongitude);
    }

}