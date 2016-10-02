package com.sonda.emsysmobile.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ssainz on 10/2/16.
 */
public class DateUtils {
    public static String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }
}
