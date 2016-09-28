package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 9/25/16.
 */
public class DtoAddress {

    @SerializedName("calle")
    public String street;

    @SerializedName("esquina")
    public String corner;

    @SerializedName("numero")
    public String number;

    @SerializedName("departamento")
    public String department;
}
