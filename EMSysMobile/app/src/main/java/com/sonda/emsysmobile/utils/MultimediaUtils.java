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

    public static Bitmap getBitmapFromBase64(String encodedImage) {
        byte[] byteArray = Base64.decode(encodedImage, Base64.DEFAULT);
        return getBitmapFromByteArray(byteArray);
    }

    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    public static boolean createDirIfNotExists(Context context, String path) {
        boolean ret = true;
//        File file = new File(Environment.getExternalStorageDirectory(), path);
        File file = new File(context.getCacheDir(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG, "No pudo crearse la carpeta de imagenes.");
                ret = false;
            }
        }
        return ret;
    }

}
