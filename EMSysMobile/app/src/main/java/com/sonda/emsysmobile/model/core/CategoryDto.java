package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 9/30/16.
 */
public class CategoryDto {

    @SerializedName("id")
    public int identifier;

    @SerializedName("codigo")
    public String code;

    @SerializedName("clave")
    public String key;

    @SerializedName("prioridad")
    public ExtensionPriority priority;

    @SerializedName("activo")
    public boolean isActive;

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ExtensionPriority getPriority() {
        return priority;
    }

    public void setPriority(ExtensionPriority priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
