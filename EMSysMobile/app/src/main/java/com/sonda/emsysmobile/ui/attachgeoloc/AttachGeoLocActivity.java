package com.sonda.emsysmobile.ui.attachgeoloc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.sonda.emsysmobile.ui.views.CustomScrollView;

import com.sonda.emsysmobile.R;

/**
 * Created by Pape on 10/24/2016.
 */

public class AttachGeoLocActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private AttachGeoLocView mGeoLocFragment = null;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    CustomScrollView mMainScrollView;
    private static final String TAG = AttachGeoLocActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_geoloc);

        // Inicializo servicio de Google para obtener ubicacion actual.
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        // Inicializacion de fragment de mapa.
        mGeoLocFragment = AttachGeoLocView.getInstance();
        mMainScrollView = (CustomScrollView) findViewById(R.id.main_scrollview);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        int extensionId = getIntent().getIntExtra("ExtensionId", -1);
        if (mLastLocation != null) {
            mGeoLocFragment
                    .initializeView(this, mMainScrollView, extensionId, mLastLocation.getLatitude(),
                            mLastLocation.getLongitude());
        } else {
            // Ubicacion por defecto en Montevideo, Uruguay.
            LatLng defaultLocation = new LatLng(-34.9, -56.1);
            mGeoLocFragment
                    .initializeView(this, mMainScrollView, extensionId, defaultLocation.latitude,
                            defaultLocation.longitude);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, getString(R.string.error_http));
    }
}