package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

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

    @SerializedName("extensiones")
    public List<ExtensionDto> extensions;

    @SerializedName("categoria")
    public CategoryDto category;

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getInformant() {
        return informant;
    }

    public void setInformant(String informant) {
        this.informant = informant;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}