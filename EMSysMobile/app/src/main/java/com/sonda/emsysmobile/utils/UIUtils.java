package com.sonda.emsysmobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.inputmethod.InputMethodManager;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.views.dialogs.SimpleDialog;

/**
 * Created by ssainz on 8/29/16.
 */
public final class UIUtils {

    private UIUtils() {
        // Debe ser privado porque no debe ser utilizado.
    }

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        if(activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static DialogFragment getSimpleDialog(String message) {
        DialogFragment dialog = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        dialog.setArguments(args);
        return dialog;
    }
}
