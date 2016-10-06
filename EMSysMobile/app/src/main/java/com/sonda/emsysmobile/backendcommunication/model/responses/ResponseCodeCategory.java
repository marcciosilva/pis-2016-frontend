package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 06-Oct-16.
 */

public enum ResponseCodeCategory {
    SUCCESS(0), INVALID_CREDENTIALS(1), NO_AUTH(2), MORE_THAN_ONE_RESOURCE(3),
    ZONES_AND_RESOURCES_SELECTED(4), RESOURCE_NOT_AVAILABLE(5);

    private int numVal;

    ResponseCodeCategory(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}