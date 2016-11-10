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

    public DescriptionDto(String user, Date date, String description, int origin) {
        this.user = user;
        this.date = date;
        this.description = description;
        this.origin = origin;
    }

    public String getUser() {
        return user;
    }

    public void setCode(String user) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionDto that = (DescriptionDto) o;

        if (origin != that.origin) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public String toString() {
        String evaluatedDescription = "";
        if(description != null)
            evaluatedDescription = description;


        return "["+ DateUtils.dateToString(date) + " - " + user + "] " + evaluatedDescription;
    }
}
