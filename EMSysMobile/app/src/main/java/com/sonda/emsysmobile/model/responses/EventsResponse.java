package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.model.core.EventDto;

import java.util.ArrayList;

/**
 * Created by ssainz on 9/25/16.
 */
public class EventsResponse extends EmsysResponse {

    /**
     * The list of events returned by the service
     */
    @SerializedName("response")
    private ArrayList<EventDto> events;

    public EventsResponse(ArrayList<EventDto> events) {
        this.events = events;
    }

    public ArrayList<EventDto> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventDto> events) {
        this.events = events;
    }
}