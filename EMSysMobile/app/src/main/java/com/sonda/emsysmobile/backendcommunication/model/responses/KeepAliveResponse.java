package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nachoprbd on 16/10/2016.
 */
public class KeepAliveResponse extends EmsysResponse {

    @SerializedName("expires_in")
    private int expirationTime;

    public final int getExpirationTime() {
        return expirationTime;
    }

    public final void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!KeepAliveResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final KeepAliveResponse other = (KeepAliveResponse) obj;
        return (super.equals(other) && expirationTime == other.expirationTime);
    }
}
