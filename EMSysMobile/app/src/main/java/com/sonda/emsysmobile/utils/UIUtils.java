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

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.ui.activities.SplashActivity;
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
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static DialogFragment getSimpleDialog(String message) {
        DialogFragment dialog = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        dialog.setArguments(args);
        return dialog;
    }

    public static void handleErrorMessage(final Context context, final int responseCode, final
    String errorMsg) {
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
                if (responseCode == ErrorCodeCategory.NO_AUTH.getNumVal()) {
                    Intent intent = new Intent(context, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else if (responseCode == ErrorCodeCategory.RESOURCE_NOT_AVAILABLE.getNumVal()) {
                    Intent intent = new Intent(context, RoleChooserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }
        });
        builder.show();
    }

    public static void handleVolleyErrorResponse(final Context context, VolleyError error,
                                                 DialogInterface.OnClickListener retryListener) {
        // Determino status code de la response (en caso de que el error sea de HTTP).
        NetworkResponse networkResponse = error.networkResponse;
        int statusCode = -1;
        if (networkResponse != null) {
            statusCode = networkResponse.statusCode;
        }
        // Determino mensaje de log.
        if (statusCode != -1) {
            Log.d(TAG, "Error HTTP " + Integer.toString(statusCode));
        } else {
            Log.d(TAG, "Error de conexión");
        }
        // Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle(R.string.error_server_communication_message);
        // Se ofrece reiniciar la aplicacion o quedarse en el mismo lugar, para no perder
        // datos.
        builder.setPositiveButton(R.string.error_connection_retry, retryListener);
        builder.setNegativeButton(R.string.error_connection_cancel, null);
        builder.show();
    }


}
