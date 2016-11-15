package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mserralta on 11/11/16.
 */

public class ResourceAssignationDto implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("recurso")
    private String resource;

    @SerializedName("descripcion")
    private List<DescriptionDto> descriptions;


    public ResourceAssignationDto(int id, String resource, List<DescriptionDto> descriptions) {
        this.id = id;
        this.resource = resource;
        this.descriptions = descriptions;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceAssignationDto)) {
            return false;
        }

        ResourceAssignationDto that = (ResourceAssignationDto) o;

        if (getId() != that.getId()) {
            return false;
        }
        if (getResource() != null ? !getResource().equals(that.getResource()) :
                that.getResource() != null) {
            return false;
        }
        return getDescriptions() != null ? getDescriptions().equals(that.getDescriptions()) :
                that.getDescriptions() == null;

    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getResource() {
        return resource;
    }

    public final void setResource(String resource) {
        this.resource = resource;
    }

    public final List<DescriptionDto> getDescriptions() {
        return descriptions;
    }

    public final void setDescriptions(List<DescriptionDto> descriptions) {
        this.descriptions = descriptions;
    }
}
