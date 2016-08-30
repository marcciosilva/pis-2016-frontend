package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 8/30/16.
 */
public class LoginResponse {

    public String username;

    @SerializedName("docNumber")
    public String document;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
