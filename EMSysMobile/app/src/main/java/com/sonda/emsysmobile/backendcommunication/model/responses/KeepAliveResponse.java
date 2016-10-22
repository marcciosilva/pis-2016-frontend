package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.KeepAliveDto;

/**
 * Created by nachoprbd on 16/10/2016.
 */
public class KeepAliveResponse extends EmsysResponse {

    @SerializedName("response")
    private KeepAliveDto keepAlive;

    public KeepAliveResponse(KeepAliveDto keepAlive) {
        this.keepAlive = keepAlive;
    }

    public final KeepAliveDto getKeepAlive() {
        return keepAlive;
    }

    public final void setKeepAlive(KeepAliveDto keep_alive) {
        this.keepAlive = keep_alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final KeepAliveResponse that = (KeepAliveResponse) o;

        return keepAlive != null ? keepAlive.equals(that.keepAlive) : that.keepAlive == null;

    }

}
