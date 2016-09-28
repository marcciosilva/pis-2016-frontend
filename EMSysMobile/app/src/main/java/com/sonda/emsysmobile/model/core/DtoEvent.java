package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ssainz on 9/25/16.
 */

public class DtoEvent {

    @SerializedName("id")
    public int identifier;

    @SerializedName("informante")
    public String informant;

    @SerializedName("telefono")
    public String phone;

    @SerializedName("time_stamp")
    public Date modifiedDate;

    @SerializedName("date")
    public Date createdDate;

    @SerializedName("en_proceso")
    public boolean inProcess;

    @SerializedName("origen")
    public String origin;

    @SerializedName("cod_sector")
    public String sectorCode;

    @SerializedName("estado")
    public EventState state;

    @SerializedName("direccion")
    public DtoAddress address;
}