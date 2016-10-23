package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ssainz on 9/30/16.
 */
public enum CategoryPriority implements Serializable {
    @SerializedName("alta")
    HIGH,

    @SerializedName("media")
    MEDIUM,

    @SerializedName("baja")
    LOW
}
