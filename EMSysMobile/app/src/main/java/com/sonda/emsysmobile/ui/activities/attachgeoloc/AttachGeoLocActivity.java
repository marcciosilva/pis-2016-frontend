package com.sonda.emsysmobile.ui.activities.attachgeoloc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sonda.emsysmobile.ui.views.CustomScrollView;

import com.sonda.emsysmobile.R;

/**
 * Created by Pape on 10/24/2016.
 */

public class AttachGeoLocActivity extends AppCompatActivity {

    private AttachGeoLocView mGeoLocFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_geoloc);

        // Inicializacion de fragment de mapa.
        mGeoLocFragment = AttachGeoLocView.getInstance();
        CustomScrollView mainScrollView = (CustomScrollView) findViewById(R.id.main_scrollview);
        mGeoLocFragment.initializeView(this, mainScrollView);
    }
}
