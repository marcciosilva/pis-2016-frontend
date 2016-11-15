package com.sonda.emsysmobile.utils;

import android.content.Context;

import com.sonda.emsysmobile.R;

/**
 * Created by ssainz on 11/8/16.
 */
public class GoogleTimeAgo {

    /*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private GoogleTimeAgo() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static String getTimeAgo(long time, Context context) {
        long localTime = time;
        final long l = 1000000000000L;
        if (localTime < l) {
            // if timestamp given in seconds, convert to millis
            final int i = 1000;
            localTime *= i;
        }

        long now = System.currentTimeMillis();
        if (localTime > now || localTime <= 0) {
            return null;
        }

        final long diff = now - localTime;
        if (diff < MINUTE_MILLIS) {
            return context.getString(R.string.date_text_just_now);
        } else {
            final int i = 2;
            if (diff < i * MINUTE_MILLIS) {
                return context.getString(R.string.date_text_a_minute_ago);
            } else {
                final int i1 = 50;
                if (diff < i1 * MINUTE_MILLIS) {
                    return context.getResources()
                            .getString(R.string.date_text_minutes_ago, diff / MINUTE_MILLIS);
                } else {
                    final int i2 = 90;
                    if (diff < i2 * MINUTE_MILLIS) {
                        return context.getString(R.string.date_text_an_hour_ago);
                    } else {
                        final int i3 = 24;
                        if (diff < i3 * HOUR_MILLIS) {
                            return context.getResources()
                                    .getString(R.string.date_text_hours_ago, diff / HOUR_MILLIS);
                        } else {
                            final int i4 = 48;
                            if (diff < i4 * HOUR_MILLIS) {
                                return context.getString(R.string.date_text_yesterday);
                            } else {
                                return diff / DAY_MILLIS +
                                        context.getString(R.string.date_text_days_ago);
                            }
                        }
                    }
                }
            }
        }
    }
}
