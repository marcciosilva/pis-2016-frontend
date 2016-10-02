package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 9/30/16.
 */
public enum CategoryPriority {
    @SerializedName("baja")
    LOW,

    @SerializedName("media")
    MEDIUM,

    @SerializedName("alta")
    HIGH
}
