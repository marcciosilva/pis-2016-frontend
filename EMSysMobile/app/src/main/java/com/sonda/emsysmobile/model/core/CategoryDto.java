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
    public CategoryPriority priority;

    @SerializedName("activo")
    public boolean isActive;

    public CategoryDto(int identifier, String code, String key, CategoryPriority priority, boolean isActive) {
        this.identifier = identifier;
        this.code = code;
        this.key = key;
        this.priority = priority;
        this.isActive = isActive;
    }

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

    public CategoryPriority getPriority() {
        return priority;
    }

    public void setPriority(CategoryPriority priority) {
        this.priority = priority;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDto that = (CategoryDto) o;

        if (identifier != that.identifier) return false;
        if (isActive != that.isActive) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        return priority == that.priority;

    }

}
