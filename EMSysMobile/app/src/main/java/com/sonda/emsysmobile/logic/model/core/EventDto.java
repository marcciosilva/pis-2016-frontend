package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by ssainz on 9/25/16.
 */
public class EventDto {

    @SerializedName("id")
    private int identifier;

    @SerializedName("informante")
    private String informant;

    @SerializedName("telefono")
    private String phone;

    @SerializedName("time_stamp")
    private Date timeStamp;

    @SerializedName("fecha")
    private Date createdDate;

    @SerializedName("en_proceso")
    private boolean inProcess;

    @SerializedName("origen")
    private String origin;

    @SerializedName("cod_sector")
    private String sectorCode;

    @SerializedName("calle")
    private String street;

    @SerializedName("esquina")
    private String corner;

    @SerializedName("numero")
    private String number;

    @SerializedName("departamento")
    private String department;

    @SerializedName("extensiones")
    private List<ExtensionDto> extensions;

    @SerializedName("categoria")
    private CategoryDto category;

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

    public final int getIdentifier() {
        return identifier;
    }

    public final void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public final String getInformant() {
        return informant;
    }

    public final void setInformant(String informant) {
        this.informant = informant;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = phone;
    }

    public final Date getTimeStamp() {
        return timeStamp;
    }

    public final void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public final Date getCreatedDate() {
        return createdDate;
    }

    public final void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public final boolean isInProcess() {
        return inProcess;
    }

    public final void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public final String getOrigin() {
        return origin;
    }

    public final void setOrigin(String origin) {
        this.origin = origin;
    }

    public final String getSectorCode() {
        return sectorCode;
    }

    public final void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public final String getStreet() {
        return street;
    }

    public final void setStreet(String street) {
        this.street = street;
    }

    public final String getCorner() {
        return corner;
    }

    public final void setCorner(String corner) {
        this.corner = corner;
    }

    public final String getNumber() {
        return number;
    }

    public final void setNumber(String number) {
        this.number = number;
    }

    public final String getDepartment() {
        return department;
    }

    public final void setDepartment(String department) {
        this.department = department;
    }

    public final List<ExtensionDto> getExtensions() {
        return extensions;
    }

    public final void setExtensions(List<ExtensionDto> extensions) {
        this.extensions = extensions;
    }

    public final CategoryDto getCategory() {
        return category;
    }

    public final void setCategory(CategoryDto category) {
        this.category = category;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDto eventDto = (EventDto) o;

        if (identifier != eventDto.identifier) {
            return false;
        }
        if (inProcess != eventDto.inProcess) {
            return false;
        }
        if (informant != null ? !informant.equals(eventDto.informant) : eventDto.informant != null) {
            return false;
        }
        if (phone != null ? !phone.equals(eventDto.phone) : eventDto.phone != null) {
            return false;
        }
        if (timeStamp != null ? !timeStamp.equals(eventDto.timeStamp) : eventDto.timeStamp != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(eventDto.createdDate) : eventDto.createdDate != null) {
            return false;
        }
        if (origin != null ? !origin.equals(eventDto.origin) : eventDto.origin != null) {
            return false;
        }
        if (sectorCode != null ? !sectorCode.equals(eventDto.sectorCode) : eventDto.sectorCode != null) {
            return false;
        }
        if (street != null ? !street.equals(eventDto.street) : eventDto.street != null) {
            return false;
        }
        if (corner != null ? !corner.equals(eventDto.corner) : eventDto.corner != null) {
            return false;
        }
        if (number != null ? !number.equals(eventDto.number) : eventDto.number != null) {
            return false;
        }
        if (department != null ? !department.equals(eventDto.department) : eventDto.department != null) {
            return false;
        }
        if (extensions != null ? !extensions.equals(eventDto.extensions) : eventDto.extensions != null) {
            return false;
        }
        return category != null ? category.equals(eventDto.category) : eventDto.category == null;

    }
}