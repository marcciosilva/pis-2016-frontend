package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 27-Oct-16.
 */

public class ImageDataDto {

    @SerializedName("nombre")
    private String name;

    @SerializedName("file_data")
    private String data;

    public final String getName() {
        return name;
    }

    public final String getData() {
        return data;
    }

}
