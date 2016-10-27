package com.sonda.emsysmobile.logic.model.core.attachments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by marccio on 10/20/16.
 */

public class ImageDto implements Serializable {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("file_data")
    private byte[] data;

    public ImageDto(String nombre) {
        this.nombre = nombre;
    }

    public final String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageDto imageDto = (ImageDto) o;

        if (nombre != null ? !nombre.equals(imageDto.nombre) : imageDto.nombre != null) {
            return false;
        }
        return Arrays.equals(data, imageDto.data);

    }

}
