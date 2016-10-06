package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 10/2/16.
 */

public abstract class ErrorResponse {

    @SerializedName("msg")
    private String msg;

    public final String getMsg() {
        return msg;
    }

    public final void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!ErrorResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ErrorResponse other = (ErrorResponse) obj;
        return (msg == null || msg.equals(other.msg));
    }

}
