package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 9/25/16.
 */
public class EmsysResponse {

    @SerializedName("cod")
    private int code;

    public final int getCode() {
        return code;
    }

    public final void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!EmsysResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final EmsysResponse other = (EmsysResponse) obj;
        return (code == other.code);
    }

}
