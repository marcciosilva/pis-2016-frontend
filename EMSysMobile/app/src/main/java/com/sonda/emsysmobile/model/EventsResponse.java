package com.sonda.emsysmobile.model;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.model.core.DtoEvent;

import java.util.ArrayList;

/**
 * Created by ssainz on 9/25/16.
 */
public class EventsResponse {

    /**
     * Response code
     */
    @SerializedName("cod")
    private int responseCode;

    /**
     * The list of events returned by the service
     */
    @SerializedName("response")
    private ArrayList<DtoEvent> events;
}