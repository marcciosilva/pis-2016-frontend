package com.sonda.emsysmobile.model.core;

import java.util.Date;

/**
 * Created by ssainz on 9/30/16.
 */
public class ExtensionDto {

//    "id":1,
//    "descripción":"descripción de la extension",
//    "estado":"estado_extensión",
//    "timeStamp":"24-09-2016 2:44:33",
//    "categoria":null,

    public int identifier;

    public String description;

    public ExtensionState extensionState;

    public Date timeStamp;

    public CategoryDto category;
}
