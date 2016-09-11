package com.sonda.emsysmobile.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ssainz on 8/29/16.
 */
public class UIUtils {
    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        if(activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
