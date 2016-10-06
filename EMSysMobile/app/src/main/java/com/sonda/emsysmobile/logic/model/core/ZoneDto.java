package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 9/28/16.
 */

public class ZoneDto implements Serializable {

    @SerializedName("nombre")
    private String name;

    @SerializedName("id")
    private int identifier;

    @SerializedName("nombre_ue")
    private String execUnitName;

    public ZoneDto(String name, int identifier, String execUnitName) {
        this.name = name;
        this.identifier = identifier;
        this.execUnitName = execUnitName;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final int getIdentifier() {
        return identifier;
    }

    public final void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public final String getExecUnitName() {
        return execUnitName;
    }

    public final void setExecUnitName(String execUnitName) {
        this.execUnitName = execUnitName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZoneDto zoneDto = (ZoneDto) o;

        if (identifier != zoneDto.identifier) {
            return false;
        }
        if (name != null ? !name.equals(zoneDto.name) : zoneDto.name != null) {
            return false;
        }
        return execUnitName != null ? execUnitName.equals(zoneDto.execUnitName) : zoneDto.execUnitName == null;

    }

}
