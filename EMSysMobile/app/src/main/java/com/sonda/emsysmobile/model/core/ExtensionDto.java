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

    @SerializedName("timeStamp")
    public Date timeStamp;

    @SerializedName("categoria")
    public CategoryDto category;

    @SerializedName("zona")
    public ZoneDto zone;

    private transient EventDto event;

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
}
