package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by marccio on 10/20/16.
 */

public class ImageDescriptionDto implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("usuario")
    private String user;

    @SerializedName("fecha_envio")
    private Date deliveryDate;

    @SerializedName("id_extension")
    private int extensionId;

    private String imageUrl;

    public final int getId() {
        return id;
    }

    public final String getUser() {
        return user;
    }

    public final Date getDeliveryDate() {
        return deliveryDate;
    }

    public final int getExtensionId() {
        return extensionId;
    }

    public final String getImageUrl() {
        return imageUrl;
    }

    public final void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
