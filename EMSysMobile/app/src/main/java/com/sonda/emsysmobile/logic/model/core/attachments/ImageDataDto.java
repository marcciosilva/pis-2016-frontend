package com.sonda.emsysmobile.logic.model.core.attachments;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by marccio on 27-Oct-16.
 */

public class ImageDataDto implements Parcelable {

    @SerializedName("nombre")
    private String name;

    @SerializedName("file_data")
    private String data;

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }


    protected ImageDataDto(Parcel in) {
        name = in.readString();
        data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(data);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ImageDataDto> CREATOR = new Parcelable.Creator<ImageDataDto>() {
        @Override
        public ImageDataDto createFromParcel(Parcel in) {
            return new ImageDataDto(in);
        }

        @Override
        public ImageDataDto[] newArray(int size) {
            return new ImageDataDto[size];
        }
    };
}
