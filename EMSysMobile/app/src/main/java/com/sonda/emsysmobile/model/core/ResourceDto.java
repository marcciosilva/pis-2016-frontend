package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 9/28/16.
 */

public class ResourceDto implements Serializable {

    @SerializedName("codigo")
    private String code;

    @SerializedName("id")
    private int id;


    public ResourceDto(String code, int id) {
        this.code = code;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (code != other.code || id != other.id) {
            return false;
        }
        return true;
    }
}
