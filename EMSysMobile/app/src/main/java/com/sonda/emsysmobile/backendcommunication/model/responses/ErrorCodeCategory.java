package com.sonda.emsysmobile.backendcommunication.model.responses;

/**
 * Created by marccio on 06-Oct-16.
 */

public enum ErrorCodeCategory {
    LOGIC_ERROR(-1), SUCCESS(0), INVALID_CREDENTIALS(1), NO_AUTH(2), ZONES_AND_RESOURCES_SELECTED(3),
    RESOURCE_NOT_AVAILABLE(4), TIME_ALREADY_REPORTED(20);

    private int numVal;

    ErrorCodeCategory(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}