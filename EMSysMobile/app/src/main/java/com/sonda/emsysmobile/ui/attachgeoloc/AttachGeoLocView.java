package com.sonda.emsysmobile.ui.attachgeoloc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.utils.UIUtils;

/**
 * Created by Pape on 10/24/2016.
 */

public class AttachGeoLocView extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = AttachGeoLocView.class.getName();
    private AttachGeoLocMapView mGeoLocFragment = null;
    private GoogleApiClient mGoogleApiClient;
    private ProgressBar mProgressBar;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_geoloc);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
        mGeoLocFragment = AttachGeoLocMapView.getInstance();
        Button mConfirmationButton = (Button) findViewById(R.id.button_send_geolocation);
        mConfirmationButton.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    protected final void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected final void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public final void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        int extensionId = getIntent().getIntExtra("ExtensionId", -1);
        if (mLastLocation != null) {
            mGeoLocFragment
                    .initializeView(this, extensionId, mLastLocation.getLatitude(),
                            mLastLocation.getLongitude());
        } else {
            // Ubicacion por defecto en Montevideo, Uruguay.
            final double v = -34.9;
            final double v1 = -56.1;
            LatLng defaultLocation = new LatLng(v, v1);
            mGeoLocFragment
                    .initializeView(this, extensionId, defaultLocation.latitude,
                            defaultLocation.longitude);
        }
    }

    @Override
    public final void onConnectionSuspended(int i) {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, getString(R.string.error_http));
    }

    @Override
    public final void onClick(View view) {
        if (view.getId() == R.id.button_send_geolocation) {
            mProgressBar.setVisibility(View.VISIBLE);
            if (!AttachGeoLocPresenter.sendGeoLocation(this)) {
                DialogFragment dialog = UIUtils.getSimpleDialog(getString(R.string
                        .attach_geolocation_null_selection_string));
                dialog.show(getSupportFragmentManager(), TAG);
            }
        }
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        // Se maneja la flecha de ir hacia atras.
        if (item.getItemId() == android.R.id.home) {
            // Cierra la Activity y vuelve a la Activity anterior (si la hubo).
            onBackPressed();
            return true;
        }
        return false;
    }
}
