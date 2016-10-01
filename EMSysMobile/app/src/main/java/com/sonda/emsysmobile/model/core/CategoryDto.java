package com.sonda.emsysmobile.model.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ssainz on 9/30/16.
 */
public class CategoryDto {

//    "id":3,
//    "codigo":"codigo2",
//    "clave":"clave2",
//    "prioridad":"valor_prioridad",
//    "activo":true

    @SerializedName("id")
    public int identifier;

    @SerializedName("codigo")
    public String code;

    @SerializedName("clave")
    public String key;

    @SerializedName("prioridad")
    public ExtensionPriority priority;

    @SerializedName("activo")
    public boolean isActive;
}
