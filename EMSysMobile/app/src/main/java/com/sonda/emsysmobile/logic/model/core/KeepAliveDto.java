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

    public final int getExpirationTime() {
        return expirationTime;
    }

    public final void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }
}
