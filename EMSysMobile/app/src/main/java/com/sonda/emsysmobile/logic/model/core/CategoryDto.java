package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ssainz on 9/30/16.
 */
public class CategoryDto implements Serializable {

    @SerializedName("id")
    private int identifier;

    @SerializedName("codigo")
    private String code;

    @SerializedName("clave")
    private String key;

    @SerializedName("prioridad")
    private CategoryPriority priority;

    @SerializedName("activo")
    private boolean isActive;

    public CategoryDto(int identifier, String code, String key, CategoryPriority priority,
                       boolean isActive) {
        this.identifier = identifier;
        this.code = code;
        this.key = key;
        this.priority = priority;
        this.isActive = isActive;
    }

    public final int getIdentifier() {
        return identifier;
    }

    public final void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public final String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final String getKey() {
        return key;
    }

    public final void setKey(String key) {
        this.key = key;
    }

    public final CategoryPriority getPriority() {
        return priority;
    }

    public final void setPriority(CategoryPriority priority) {
        this.priority = priority;
    }

    public final boolean isActive() {
        return isActive;
    }

    public final void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDto that = (CategoryDto) o;

        if (identifier != that.identifier) {
            return false;
        }
        if (isActive != that.isActive) {
            return false;
        }
        if (code != null ? !code.equals(that.code) : that.code != null) {
            return false;
        }
        if (key != null ? !key.equals(that.key) : that.key != null) {
            return false;
        }
        return priority == that.priority;

    }

}
