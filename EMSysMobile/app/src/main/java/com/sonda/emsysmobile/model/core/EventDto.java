package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ssainz on 9/25/16.
 */

public class EventDto {

    @SerializedName("id")
    public int identifier;

    @SerializedName("informante")
    public String informant;

    @SerializedName("telefono")
    public String phone;

    @SerializedName("time_stamp")
    public Date timeStamp;

    @SerializedName("fecha")
    public Date createdDate;

    @SerializedName("en_proceso")
    public boolean inProcess;

    @SerializedName("origen")
    public String origin;

    @SerializedName("cod_sector")
    public String sectorCode;

    @SerializedName("calle")
    public String street;

    @SerializedName("esquina")
    public String corner;

    @SerializedName("numero")
    public String number;

    @SerializedName("departamento")
    public String department;
}