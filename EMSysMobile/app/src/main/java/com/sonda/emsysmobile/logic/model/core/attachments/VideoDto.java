package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 10/20/16.
 */

public class VideoDto implements Serializable {

    @SerializedName("path")
    private String path;

    public VideoDto(String path) {
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

        VideoDto videoDto = (VideoDto) o;

        return path != null ? path.equals(videoDto.path) : videoDto.path == null;

    }

}
