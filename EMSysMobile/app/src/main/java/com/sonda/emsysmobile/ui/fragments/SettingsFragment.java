package com.sonda.emsysmobile.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.sonda.emsysmobile.R;

/**
 * Created by marccio on 10-Oct-16.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se cargan las preferencias del recurso en XML.
        addPreferencesFromResource(R.xml.preferences);
    }

}