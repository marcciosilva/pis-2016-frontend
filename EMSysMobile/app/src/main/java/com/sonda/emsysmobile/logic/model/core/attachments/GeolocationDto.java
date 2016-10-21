package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by marccio on 10/20/16.
 */

public class GeolocationDto implements Serializable {

    @SerializedName("idExtension")
    private int extensionIdentifier;

    @SerializedName("usuario")
    private String user;

    @SerializedName("fecha_envio")
    private Date createdDate;

    @SerializedName("latitud")
    private double latitude;

    @SerializedName("longitud")
    private double longitude;

    public GeolocationDto(int extensionIdentifier, String user, Date createdDate, double latitude, double longitude) {
        this.extensionIdentifier = extensionIdentifier;
        this.user = user;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getExtensionIdentifier() {
        return extensionIdentifier;
    }

    public void setExtensionIdentifier(int extensionIdentifier) {
        this.extensionIdentifier = extensionIdentifier;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeolocationDto that = (GeolocationDto) o;

        if (extensionIdentifier != that.extensionIdentifier) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return createdDate != null ? createdDate.equals(that.createdDate) : that.createdDate == null;

    }

}
