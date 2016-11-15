package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/30/16.
 */

public class AuthInnerResponse extends ErrorResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expirationTime;

    public final String getAccessToken() {
        return accessToken;
    }

    public final void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

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
        if (!AuthInnerResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final AuthInnerResponse other = (AuthInnerResponse) obj;
        return (super.equals(other) && accessToken.equals(other.accessToken) &&
                expirationTime == other.expirationTime);
    }

}