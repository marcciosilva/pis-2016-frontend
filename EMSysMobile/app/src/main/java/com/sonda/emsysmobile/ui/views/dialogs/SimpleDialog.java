package com.sonda.emsysmobile.ui.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.sonda.emsysmobile.R;

/**
 * Created by ssainz on 10/5/16.
 */
public class SimpleDialog extends DialogFragment {

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String message = args.getString("message", "");

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                })
                .create();
    }
}
