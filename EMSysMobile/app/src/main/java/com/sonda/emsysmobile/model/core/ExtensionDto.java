package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ssainz on 9/30/16.
 */
public class ExtensionDto {

    @SerializedName("id")
    public int identifier;

    @SerializedName("descripcion")
    public String description;

    @SerializedName("estado")
    public ExtensionState extensionState;

    @SerializedName("time_stamp")
    public Date timeStamp;

    @SerializedName("categoria")
    public CategoryDto category;

    @SerializedName("zona")
    public ZoneDto zone;

    private transient EventDto event;

    public ExtensionDto(int identifier, String description, ExtensionState extensionState,
                        Date timeStamp, CategoryDto category, ZoneDto zone, EventDto event) {
        this.identifier = identifier;
        this.description = description;
        this.extensionState = extensionState;
        this.timeStamp = timeStamp;
        this.category = category;
        this.zone = zone;
        this.event = event;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExtensionState getExtensionState() {
        return extensionState;
    }

    public void setExtensionState(ExtensionState extensionState) {
        this.extensionState = extensionState;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public ZoneDto getZone() {
        return zone;
    }

    public void setZone(ZoneDto zone) {
        this.zone = zone;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        // No se hace el chequeo de igualdad de EventDto debido a la dependencia circular
        // entre esta y esa clase.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtensionDto that = (ExtensionDto) o;

        if (identifier != that.identifier) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (extensionState != that.extensionState) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null)
            return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        return zone != null ? zone.equals(that.zone) : that.zone == null;

    }

}
