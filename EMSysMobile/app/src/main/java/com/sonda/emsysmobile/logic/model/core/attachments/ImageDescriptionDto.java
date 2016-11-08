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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
