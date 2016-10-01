package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ssainz on 9/25/16.
 */
public class EmsysResponse {

    @SerializedName("cod")
    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
