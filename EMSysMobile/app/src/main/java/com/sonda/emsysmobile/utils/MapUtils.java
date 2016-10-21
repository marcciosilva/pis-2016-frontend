package com.sonda.emsysmobile.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by marccio on 10/20/16.
 */

public final class MapUtils {

    private MapUtils() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static boolean areBoundsTooSmall(LatLngBounds bounds, int minDistanceInMeter) {
        float[] result = new float[1];
        Location.distanceBetween(bounds.southwest.latitude, bounds.southwest.longitude, bounds.northeast.latitude, bounds.northeast.longitude, result);
        return result[0] < minDistanceInMeter;
    }

}
