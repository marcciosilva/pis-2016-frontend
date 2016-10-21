package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 10/20/16.
 */

public class AudioDto implements Serializable {

    @SerializedName("path")
    private String path;

    public AudioDto(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudioDto audioDto = (AudioDto) o;

        return path != null ? path.equals(audioDto.path) : audioDto.path == null;

    }

}
