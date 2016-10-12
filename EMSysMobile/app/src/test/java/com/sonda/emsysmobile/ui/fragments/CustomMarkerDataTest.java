package com.sonda.emsysmobile.ui.fragments;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 12-Oct-16.
 */
public class CustomMarkerDataTest {

    private CustomMarkerData mCustomMarkerData;
    private String mTitle;
    private String mDescription;
    private LatLng mCoordinates;

    @Before
    public void setUp() throws Exception {
        mTitle = "title";
        mDescription = "description";
        mCoordinates = new LatLng(0, 0);
        mCustomMarkerData = new CustomMarkerData(mTitle, mDescription, mCoordinates);
    }

    @Test
    public void getTitle() throws Exception {
        assertTrue(mCustomMarkerData.getTitle().equals(mTitle));
    }

    @Test
    public void setTitle() throws Exception {
        String testTitle = "testTitle";
        mCustomMarkerData.setTitle(testTitle);
        assertTrue(mCustomMarkerData.getTitle().equals(testTitle));
    }

    @Test
    public void getDescription() throws Exception {
        assertTrue(mCustomMarkerData.getDescription().equals(mDescription));
    }

    @Test
    public void setDescription() throws Exception {
        String testDescription = "testDescription";
        mCustomMarkerData.setDescription(testDescription);
        assertTrue(mCustomMarkerData.getDescription().equals(testDescription));
    }

    @Test
    public void getCoordinates() throws Exception {
        assertTrue(mCustomMarkerData.getCoordinates().equals(mCoordinates));
    }

    @Test
    public void setCoordinates() throws Exception {
        LatLng testCoordinates = new LatLng(1.0, 1.0);
        mCustomMarkerData.setCoordinates(testCoordinates);
        assertTrue(mCustomMarkerData.getCoordinates().equals(testCoordinates));
    }

}