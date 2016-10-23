package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;

import java.util.List;

/**
 * Created by ssainz on 9/25/16.
 */
public class ExternalServiceResponse extends EmsysResponse {

    /**
     * Devuelve la lista de ítems devueltos por el servicio externo
     * Estos ítems contiene cada uno 10 strings
     */
    @SerializedName("response")
    private List<ExternalServiceItemDto> items;

    public ExternalServiceResponse(List<ExternalServiceItemDto> items) {
        this.items = items;
    }

    public final List<ExternalServiceItemDto> getItems() {
        return items;
    }

    public final void setItems(List<ExternalServiceItemDto> items) {
        this.items = items;
    }
}