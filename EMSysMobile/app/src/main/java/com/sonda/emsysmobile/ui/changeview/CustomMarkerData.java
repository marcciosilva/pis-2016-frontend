package com.sonda.emsysmobile.ui.changeview;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by marccio on 12-Oct-16.
 */

public class CustomMarkerData {

    private String title;
    private String description;
    private LatLng coordinates;

    public CustomMarkerData(String title, String description, LatLng coordinates) {
        this.title = title;
        this.description = description;
        this.coordinates = coordinates;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final LatLng getCoordinates() {
        return coordinates;
    }

    public final void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}