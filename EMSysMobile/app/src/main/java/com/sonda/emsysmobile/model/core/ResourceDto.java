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

    public final String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }


    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceDto that = (ResourceDto) o;

        if (id != that.id) {
            return false;
        }
        return code != null ? code.equals(that.code) : that.code == null;

    }

}
