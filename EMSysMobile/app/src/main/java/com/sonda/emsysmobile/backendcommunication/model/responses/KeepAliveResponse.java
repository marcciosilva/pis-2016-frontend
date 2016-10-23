package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.KeepAliveDto;

/**
 * Created by nachoprbd on 16/10/2016.
 */
public class KeepAliveResponse extends EmsysResponse {

    @SerializedName("response")
    private KeepAliveDto keepAlive;

    public final KeepAliveDto getKeepAlive() {
        return keepAlive;
    }

    public final void setExpirationTime(KeepAliveDto keep_alive) {
        this.keepAlive = keep_alive;
    }
/*
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
    }*/
}
