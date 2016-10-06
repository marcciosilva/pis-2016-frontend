package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.model.core.EventDto;

import java.util.List;

/**
 * Created by ssainz on 9/25/16.
 */
public class EventsResponse extends EmsysResponse {

    /**
     * The list of events returned by the service
     */
    @SerializedName("response")
    private List<EventDto> events;

    public EventsResponse(List<EventDto> events) {
        this.events = events;
    }

    public final List<EventDto> getEvents() {
        return events;
    }

    public final void setEvents(List<EventDto> events) {
        this.events = events;
    }
}