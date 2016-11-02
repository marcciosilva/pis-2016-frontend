package com.sonda.emsysmobile.logic.model.core.attachments;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
