package com.sonda.emsysmobile.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.fragments.SettingsFragment;

import java.util.List;

/**
 * Created by marccio on 10-Oct-16.
 */

public class SettingsActivity extends PreferenceActivity {

    private static final String TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        checkValues();
    }


    private void checkValues()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String backendUrl = sharedPrefs.getString("backendUrl", "Not assigned");
        // Se imprimen preferencias actuales.
        String msg = "Current preferences' values: ";
        msg += "\n backendUrl = " + backendUrl;
        Log.d(TAG, msg);
    }

    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.headers_preference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return SettingsFragment.class.getName().equals(fragmentName);
    }

}