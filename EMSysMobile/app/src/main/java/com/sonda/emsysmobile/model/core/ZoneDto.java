package com.sonda.emsysmobile.model.core;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getExecUnitName() {
        return execUnitName;
    }

    public void setExecUnitName(String execUnitName) {
        this.execUnitName = execUnitName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!ZoneDto.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ZoneDto other = (ZoneDto) obj;
        if (name != other.name || identifier != other.identifier
                || execUnitName != other.execUnitName) {
            return false;
        }
        return true;
    }
}
