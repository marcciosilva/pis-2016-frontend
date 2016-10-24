package com.sonda.emsysmobile.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sonda.emsysmobile.BaseMockTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 24-Oct-16.
 */
public class MapUtilsTest extends BaseMockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void areBoundsTooSmall() throws Exception {
        assertTrue(MapUtils.areBoundsTooSmall(new LatLngBounds(new LatLng(0.0, 0.0), new LatLng(0.0, 0.1)),
                100));
    }

}