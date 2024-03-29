package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ssainz on 9/30/16.
 */
public enum ExtensionState implements Serializable {
    @SerializedName("faltadespachar")
    DISPATCH_MISSING,

    @SerializedName("despachado")
    DISPATCHED,

    @SerializedName("cerrado")
    CLOSED
}
