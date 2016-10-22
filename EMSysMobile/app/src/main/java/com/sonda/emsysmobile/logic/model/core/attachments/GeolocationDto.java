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

    public GeolocationDto(int extensionIdentifier, String user, Date createdDate, double
            latitude, double longitude) {
        this.extensionIdentifier = extensionIdentifier;
        this.user = user;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public final int getExtensionIdentifier() {
        return extensionIdentifier;
    }

    public final void setExtensionIdentifier(int extensionIdentifier) {
        this.extensionIdentifier = extensionIdentifier;
    }

    public final String getUser() {
        return user;
    }

    public final void setUser(String user) {
        this.user = user;
    }

    public final Date getCreatedDate() {
        return createdDate;
    }

    public final void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public final double getLatitude() {
        return latitude;
    }

    public final void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public final double getLongitude() {
        return longitude;
    }

    public final void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
