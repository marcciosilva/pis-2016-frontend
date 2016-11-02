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

    @SerializedName("idExtension")
    private int extensionId;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public int getExtensionId() {
        return extensionId;
    }
}
