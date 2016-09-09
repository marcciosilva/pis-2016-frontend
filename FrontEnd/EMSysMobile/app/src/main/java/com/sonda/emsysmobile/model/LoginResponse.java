package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 8/30/16.
 */
public class LoginResponse {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("expires_in")
    public int expirationTime;

    public String getAccessToken() { return accessToken; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public int getExpirationTime() { return expirationTime; }

    public void setExpirationTime(int expirationTime) { this.expirationTime = expirationTime; }


}
