package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ssainz on 9/30/16.
 */
public class ExtensionDto implements Serializable {

    @SerializedName("id")
    private int identifier;

    @SerializedName("descripcion_supervisor")
    private String description;

    @SerializedName("estado")
    private ExtensionState extensionState;

    @SerializedName("time_stamp")
    private Date timeStamp;

    @SerializedName("segunda_categoria")
    private CategoryDto category;

    @SerializedName("zona")
    private ZoneDto zone;

    @SerializedName("imagenes")
    private List<ImageDescriptionDto> imageDescriptions;

    @SerializedName("descripcion_despachadores")
    private List<DescriptionDto> dispatcher_description;

    @SerializedName("asignaciones_recursos")
    private List<ResourceAssignationDto> resource_assignations;

    @SerializedName("recursos")
    private List<String> resources;

    /**
     * When this boolean is true, an indicator is shown
     * for this extension in the extensions list.
     */
    private boolean isModified;

    @SerializedName("geo_ubicaciones")
    private List<GeolocationDto> geolocations;

    private transient EventDto event;

    public ExtensionDto(int identifier, String description, ExtensionState extensionState,
                        Date timeStamp, CategoryDto category, ZoneDto zone,
                        List<GeolocationDto> geolocations, EventDto event, List<String> resources) {
        this.identifier = identifier;
        this.description = description;
        this.extensionState = extensionState;
        this.timeStamp = timeStamp;
        this.category = category;
        this.zone = zone;
        this.geolocations = geolocations;
        this.event = event;
        this.resources = resources;
    }

    public final int getIdentifier() {
        return identifier;
    }

    public final void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final ExtensionState getExtensionState() {
        return extensionState;
    }

    public final void setExtensionState(ExtensionState extensionState) {
        this.extensionState = extensionState;
    }

    public final Date getTimeStamp() {
        return timeStamp;
    }

    public final void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public final CategoryDto getCategory() {
        return category;
    }

    public final void setCategory(CategoryDto category) {
        this.category = category;
    }

    public final ZoneDto getZone() {
        return zone;
    }

    public final void setZone(ZoneDto zone) {
        this.zone = zone;
    }

    public final EventDto getEvent() {
        return event;
    }

    public final void setEvent(EventDto event) {
        this.event = event;
    }

    public final boolean isModified() {
        return isModified;
    }

    public final void setModified(boolean modified) {
        isModified = modified;
    }

    public List<DescriptionDto> getDispatcherDescription() {
        return dispatcher_description;
    }

    public void setDispatcherDescription(List<DescriptionDto> dispatcher_description) {
        this.dispatcher_description = dispatcher_description;
    }

    public List<ResourceAssignationDto> getResourceAssignations() {
        return resource_assignations;
    }

    public void setResourceAssignations(List<ResourceAssignationDto> resource_assignations) {
        this.resource_assignations = resource_assignations;
    }

    public final List<String> getResources(){
        return resources;
    }

    public final void setResources(List<String> resources){
        this.resources = resources;
    }

    /**
     * Get priority for Event
     * Try to get priority for his Category, but if it does not have one, return event's category priority.
     * @return An instance of CategoryPriority Enum.
     */
    public final CategoryPriority getPriority() {
        if (category != null) {
            return category.getPriority();
        }
        CategoryDto eventCategory = event.getCategory();
        if (eventCategory != null) {
            //Should always have an event priority, just in case.
            return eventCategory.getPriority();
        }
        //Default priority is LOW.
        return CategoryPriority.LOW;
    }

    public final List<GeolocationDto> getGeolocations() {
        return geolocations;
    }

    public final void setGeolocations(List<GeolocationDto> geolocations) {
        this.geolocations = geolocations;
    }

    public List<ImageDescriptionDto> getImageDescriptions() {
        return imageDescriptions;
    }

    @Override
    public final boolean equals(Object o) {
        // No se hace el chequeo de igualdad de EventDto debido a la dependencia circular
        // entre esta y esa clase.
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtensionDto that = (ExtensionDto) o;

        if (identifier != that.identifier) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (extensionState != that.extensionState) {
            return false;
        }
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) {
            return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null) {
            return false;
        }
        if (geolocations != null ? !geolocations.equals(that.geolocations) :
                that.geolocations != null) {
            return false;
        }

        if (resources != null ? !resources.equals(that.resources) : that.resources != null){
            return false;
        }

        return zone != null ? zone.equals(that.zone) : that.zone == null;

    }
}
