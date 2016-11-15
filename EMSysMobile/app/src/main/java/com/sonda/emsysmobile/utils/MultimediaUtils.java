package com.sonda.emsysmobile.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;

/**
 * Created by marccio on 28-Oct-16.
 */

public final class MultimediaUtils {

    private static final String TAG = MultimediaUtils.class.getName();

    private MultimediaUtils() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

}
