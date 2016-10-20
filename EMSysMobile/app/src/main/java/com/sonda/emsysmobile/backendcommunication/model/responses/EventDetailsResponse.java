package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.EventDto;

import java.util.List;

/**
 * Created by mserralta on 18/10/16.
 */

public class EventDetailsResponse extends EmsysResponse {

    @SerializedName("response")
    private EventDto event;


    public EventDto getEvent() {
        return event;
    }
}