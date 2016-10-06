package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marccio on 9/28/16.
 */

public class RoleDto extends ErrorResponse implements Serializable {

    @SerializedName("zonas")
    private List<ZoneDto> zones = new ArrayList<>();

    @SerializedName("recursos")
    private List<ResourceDto> resources = new ArrayList<>();

    public RoleDto(List<ZoneDto> zones, List<ResourceDto> resources) {
        this.zones = zones;
        this.resources = resources;
    }

    public final List<ZoneDto> getZones() {
        return zones;
    }

    public final void setZones(List<ZoneDto> zones) {
        this.zones = zones;
    }

    public final List<ResourceDto> getResources() {
        return resources;
    }

    public final void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        RoleDto roleDto = (RoleDto) o;

        if (zones != null ? !zones.equals(roleDto.zones) : roleDto.zones != null) {
            return false;
        }
        return resources != null ? resources.equals(roleDto.resources) : roleDto.resources == null;

    }

}
