package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by marccio on 27-Oct-16.
 */

public class ImageDataDto {

    @SerializedName("nombre")
    private String name;

    @SerializedName("file_data")
    private byte[] data;

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageDataDto that = (ImageDataDto) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return Arrays.equals(data, that.data);

    }

}
