package com.sonda.emsysmobile.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sonda.emsysmobile.BaseMockTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by marccio on 24-Oct-16.
 */
@PrepareForTest(Location.class)
public class MapUtilsTest extends BaseMockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void areBoundsTooSmall() throws Exception {
        // TODO mejorar
        MapUtils.areBoundsTooSmall(new LatLngBounds(new LatLng(0.0, 0.0), new LatLng(0.0, 0.1)),
                100);
    }

}