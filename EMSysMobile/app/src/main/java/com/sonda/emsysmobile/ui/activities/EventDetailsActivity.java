package com.sonda.emsysmobile.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.fragments.EventDetailsFragment;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        if (savedInstanceState == null) {
            // Añadir fragmento de detalle
            Bundle arguments = new Bundle();
            arguments.putString(EventDetailsFragment.EVENT_ID,
                    getIntent().getStringExtra(EventDetailsFragment.EVENT_ID));
            EventDetailsFragment fragment = new EventDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.event_details_container, fragment)
                    .commit();
        }
    }


}
