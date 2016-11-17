package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;
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
    private boolean offlineAdded;

    public DescriptionDto(String user, Date date, String description, int origin,
                          boolean offlineAdded) {
        this.user = user;
        this.date = date;
        this.description = description;
        this.origin = origin;
        this.offlineAdded = offlineAdded;
    }

    public final boolean isOfflineAdded() {
        return offlineAdded;
    }

    public final void setOfflineAdded(boolean offlineAdded) {
        this.offlineAdded = offlineAdded;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescriptionDto)) {
            return false;
        }

        DescriptionDto that = (DescriptionDto) o;

        if (getOrigin() != that.getOrigin()) {
            return false;
        }
        if (offlineAdded != that.offlineAdded) {
            return false;
        }
        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) {
            return false;
        }
        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) {
            return false;
        }
        return getDescription() != null ? getDescription().equals(that.getDescription()) :
                that.getDescription() == null;

    }

    public final int getOrigin() {
        return origin;
    }

    public final String getUser() {
        return user;
    }

    public final void setUser(String user) {
        this.user = user;
    }

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final void setOrigin(int origin) {
        this.origin = origin;
    }

    @Override
    public final String toString() {
        String evaluatedDescription = "";
        if (description != null) {
            evaluatedDescription = description;
        }

        String evaluatedOffline = "";
        if (offlineAdded) {
            evaluatedOffline = "[OFFLINE]";
        }

        return "[" + DateUtils.dateToString(date) + " - " + user + "] " + evaluatedOffline + " " +
                evaluatedDescription;
    }
}
