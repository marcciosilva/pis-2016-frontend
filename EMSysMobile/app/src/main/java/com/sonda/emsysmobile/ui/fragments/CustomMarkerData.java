package com.sonda.emsysmobile.ui.fragments;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}