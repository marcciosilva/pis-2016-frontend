package com.sonda.emsysmobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.sonda.emsysmobile.backendcommunication.model.responses.ResponseCodeCategory;
import com.sonda.emsysmobile.ui.activities.SplashActivity;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;
import com.sonda.emsysmobile.ui.activities.login.RoleChooserActivity;
import com.sonda.emsysmobile.ui.views.dialogs.SimpleDialog;

/**
 * Created by ssainz on 8/29/16.
 */
public final class UIUtils {

    private static final String TAG = UIUtils.class.getName();

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

    public static void handleErrorMessage(final Context context, final int responseCode, final String errorMsg) {
        // Obtengo mensaje de error correspondiente al codigo.
        Log.d(TAG, "errorMsg : " + errorMsg);
        Log.d(TAG, "errorCode : " + Integer.toString(responseCode));
        //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle("Error");
        builder.setMessage(errorMsg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (responseCode == ResponseCodeCategory.NO_AUTH.getNumVal()) {
                    Intent intent = new Intent(context, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else if (responseCode == ResponseCodeCategory.RESOURCE_NOT_AVAILABLE.getNumVal()) {
                    Intent intent = new Intent(context, RoleChooserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }
        });
        builder.show();
    }

}
