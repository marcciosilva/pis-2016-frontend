package com.sonda.emsysmobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.fragments.ExtensionsFragment;
import com.sonda.emsysmobile.model.core.ExtensionDto;

public class HomeActivity extends AppCompatActivity implements ExtensionsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ExtensionsFragment extensionsFragment = new ExtensionsFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, extensionsFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto item) {

    }
}