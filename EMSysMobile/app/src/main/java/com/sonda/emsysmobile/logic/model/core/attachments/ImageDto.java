package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 10/20/16.
 */

public class ImageDto implements Serializable {

    @SerializedName("path")
    private String path;

    public ImageDto(String path) {
        this.path = path;
    }

    public final String getPath() {
        return path;
    }

    public final void setPath(String path) {
        this.path = path;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageDto imageDto = (ImageDto) o;

        return path != null ? path.equals(imageDto.path) : imageDto.path == null;

    }

}
