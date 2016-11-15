package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import com.sonda.emsysmobile.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ssainz on 9/30/16.
 */
public class DescriptionDto implements Serializable {

    @SerializedName("usuario")
    private String user;

    @SerializedName("fecha")
    private Date date;

    @SerializedName("descripcion")
    private String description;

    @SerializedName("origen")
    private int origin;

    @SerializedName("agregada_offline")
    private boolean offline_added;

    public DescriptionDto(String user, Date date, String description, int origin, boolean offline_added) {
        this.user = user;
        this.date = date;
        this.description = description;
        this.origin = origin;
        this.offline_added = offline_added;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public boolean isOfflineAdded() {
        return offline_added;
    }

    public void setOfflineAdded(boolean offline_added) {
        this.offline_added = offline_added;
    }

    @Override
    public String toString() {
        String evaluatedDescription = "";
        if(description != null)
            evaluatedDescription = description;

        String evaluatedOffline = "";
        if (offline_added)
            evaluatedOffline = "[OFFLINE]";

        return "["+ DateUtils.dateToString(date) + " - " + user + "] " + evaluatedOffline + " " + evaluatedDescription;
    }
}
