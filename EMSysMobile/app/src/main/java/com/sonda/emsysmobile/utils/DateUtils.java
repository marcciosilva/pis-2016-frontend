package com.sonda.emsysmobile.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ssainz on 10/2/16.
 */
public final class DateUtils {

    public static final String SIMPLE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    private DateUtils() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static String dateToString(Date date) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
            return df.format(date);
        }
        return "";
    }

    public static String timeAgoString(Context context, Date date) {
        if (date != null) {
            return GoogleTimeAgo.getTimeAgo(date.getTime(), context);
        }
        return "";
    }
}
