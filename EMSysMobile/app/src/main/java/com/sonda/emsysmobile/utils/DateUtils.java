package com.sonda.emsysmobile.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ssainz on 10/2/16.
 */
public final class DateUtils {

    private DateUtils() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static final String SIMPLE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    public static String dateToString(Date date) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
            return df.format(date);
        }
        return "";
    }
}
