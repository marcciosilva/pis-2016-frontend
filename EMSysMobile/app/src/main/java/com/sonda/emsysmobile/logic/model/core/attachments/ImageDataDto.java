package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marccio on 27-Oct-16.
 */

public class ImageDataDto {

    public ImageDataDto(String _name, String _data){
        this.name = _name;
        this.data = _data;
    }

    @SerializedName("nombre")
    private String name;

    @SerializedName("file_data")
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
