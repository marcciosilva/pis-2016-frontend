package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 9/28/16.
 */

public class ResourceDto implements Serializable {

    @SerializedName("codigo")
    private String code;

    public ResourceDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!ResourceDto.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ResourceDto other = (ResourceDto) obj;
        if (code != other.code) {
            return false;
        }
        return true;
    }
}
