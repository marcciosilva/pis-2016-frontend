package com.sonda.emsysmobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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

    public static void handleErrorMessage(final Context context, final int responseCode, String
            errorMsg) {
        // Obtengo mensaje de error correspondiente al codigo.
        Log.d(TAG, "errorMsg : " + errorMsg);
        Log.d(TAG, "errorCode : " + Integer.toString(responseCode));
        //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle("Error");
        if (errorMsg == null) {
            if (responseCode == ErrorCodeCategory.NETWORK_ERROR.getNumVal()) {
                errorMsg = ErrorCodeCategory.NETWORK_ERROR.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.NO_AVAILABLE_MULTIMEDIA.getNumVal()) {
                errorMsg = ErrorCodeCategory.NO_AVAILABLE_MULTIMEDIA.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.LOGIC_ERROR.getNumVal()) {
                errorMsg = ErrorCodeCategory.LOGIC_ERROR.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                errorMsg = ErrorCodeCategory.SUCCESS.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.INVALID_CREDENTIALS.getNumVal()) {
                errorMsg = ErrorCodeCategory.INVALID_CREDENTIALS.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.NO_AUTH.getNumVal()) {
                errorMsg = ErrorCodeCategory.NO_AUTH.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.RESOURCE_NOT_AVAILABLE.getNumVal()) {
                errorMsg = ErrorCodeCategory.RESOURCE_NOT_AVAILABLE.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.NOT_ALLOWED.getNumVal()) {
                errorMsg = ErrorCodeCategory.NOT_ALLOWED.getMsg(context);
            } else if (responseCode == ErrorCodeCategory.TIME_ALREADY_REPORTED.getNumVal()) {
                errorMsg = ErrorCodeCategory.TIME_ALREADY_REPORTED.getMsg(context);
            } else {
                errorMsg = context.getString(R.string.error_unkown);
            }
        }
        builder.setMessage(errorMsg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (responseCode == ErrorCodeCategory.NO_AUTH.getNumVal()) {
                    Intent intent = new Intent(context, SplashActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else if (responseCode ==
                        ErrorCodeCategory.RESOURCE_NOT_AVAILABLE.getNumVal()) {
                    Intent intent = new Intent(context, RoleChooserActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert
     *                into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((viewX < x && x < (viewX + view.getWidth())) &&
                (viewY < y && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }
}
