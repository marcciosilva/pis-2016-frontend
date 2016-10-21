package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by Pape on 20/10/2016.
 */

public class GeoLocationDto {

    @SerializedName("idExtension")
    private int extensionId;

    @SerializedName("usuario")
    private String user;

    @SerializedName("fecha_envio")
    private Date sentDate;

    @SerializedName("longitud")
    private double longitude;

    @SerializedName("latitud")
    private double latitude;

    public GeoLocationDto(int extensionId, String user, Date sentDate, double longitude, double latitude) {
        this.extensionId = extensionId;
        this.user = user;
        this.sentDate = sentDate;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
