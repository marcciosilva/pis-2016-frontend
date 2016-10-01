package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by marccio on 9/28/16.
 */

public class RoleDto implements Serializable {

    @SerializedName("zonas")
    private ArrayList<ZoneDto> zones = new ArrayList<>();

    @SerializedName("recursos")
    private ArrayList<ResourceDto> resources = new ArrayList<>();

    public RoleDto(ArrayList<ZoneDto> zones, ArrayList<ResourceDto> resources) {
        this.zones = zones;
        this.resources = resources;
    }

    public ArrayList<ZoneDto> getZones() {
        return zones;
    }

    public void setZones(ArrayList<ZoneDto> zones) {
        this.zones = zones;
    }

    public ArrayList<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(ArrayList<ResourceDto> resources) {
        this.resources = resources;
    }
}
