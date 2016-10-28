package com.sonda.emsysmobile.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by marccio on 28-Oct-16.
 */

public final class MultimediaUtils {

    private MultimediaUtils() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static Bitmap getBitmapFromBase64(String encodedImage) {
        byte[] byteArray = Base64.decode(encodedImage, Base64.DEFAULT);
        return getBitmapFromByteArray(byteArray);
    }

    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

}
