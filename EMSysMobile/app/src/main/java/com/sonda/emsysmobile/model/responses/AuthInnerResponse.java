package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 9/30/16.
 */

public class AuthInnerResponse {
    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("expires_in")
    private int mExpirationTime;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public int getExpirationTime() {
        return mExpirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        mExpirationTime = expirationTime;
    }

}