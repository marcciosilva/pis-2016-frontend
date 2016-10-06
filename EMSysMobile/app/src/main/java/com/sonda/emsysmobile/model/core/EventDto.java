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

    public EventDto(int identifier, String informant, String phone, Date timeStamp,
                    Date createdDate, boolean inProcess, String origin, String sectorCode,
                    String street, String corner, String number, String department,
                    List<ExtensionDto> extensions, CategoryDto category) {
        this.identifier = identifier;
        this.informant = informant;
        this.phone = phone;
        this.timeStamp = timeStamp;
        this.createdDate = createdDate;
        this.inProcess = inProcess;
        this.origin = origin;
        this.sectorCode = sectorCode;
        this.street = street;
        this.corner = corner;
        this.number = number;
        this.department = department;
        this.extensions = extensions;
        this.category = category;
    }

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

    public List<ExtensionDto> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionDto> extensions) {
        this.extensions = extensions;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDto eventDto = (EventDto) o;

        if (identifier != eventDto.identifier) return false;
        if (inProcess != eventDto.inProcess) return false;
        if (informant != null ? !informant.equals(eventDto.informant) : eventDto.informant != null)
            return false;
        if (phone != null ? !phone.equals(eventDto.phone) : eventDto.phone != null) return false;
        if (timeStamp != null ? !timeStamp.equals(eventDto.timeStamp) : eventDto.timeStamp != null)
            return false;
        if (createdDate != null ? !createdDate.equals(eventDto.createdDate) : eventDto.createdDate != null)
            return false;
        if (origin != null ? !origin.equals(eventDto.origin) : eventDto.origin != null)
            return false;
        if (sectorCode != null ? !sectorCode.equals(eventDto.sectorCode) : eventDto.sectorCode != null)
            return false;
        if (street != null ? !street.equals(eventDto.street) : eventDto.street != null)
            return false;
        if (corner != null ? !corner.equals(eventDto.corner) : eventDto.corner != null)
            return false;
        if (number != null ? !number.equals(eventDto.number) : eventDto.number != null)
            return false;
        if (department != null ? !department.equals(eventDto.department) : eventDto.department != null)
            return false;
        if (extensions != null ? !extensions.equals(eventDto.extensions) : eventDto.extensions != null)
            return false;
        return category != null ? category.equals(eventDto.category) : eventDto.category == null;

    }
}