package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class KeepAliveDto {
    @SerializedName("expires_in")
    private int expirationTime;

    public KeepAliveDto(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public final int getExpirationTime() {
        return expirationTime;
    }

    public final void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KeepAliveDto that = (KeepAliveDto) o;

        return expirationTime == that.expirationTime;

    }

}
