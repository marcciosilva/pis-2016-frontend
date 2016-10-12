package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by ssainz on 9/30/16.
 */
public enum CategoryPriority {
    @SerializedName("alta")
    HIGH,

    @SerializedName("media")
    MEDIUM,

    @SerializedName("baja")
    LOW
}
