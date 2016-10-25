package com.sonda.emsysmobile.ui.attachgeoloc;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.UpdateGeoLocationResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.UpdateGeoLocationRequest;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.ui.views.CustomScrollView;

import java.util.Date;

/**
 * Created by Pape on 10/24/2016.
 */

public class AttachGeoLocMapView extends SupportMapFragment
        implements GoogleMap.OnMapClickListener {

    private FragmentActivity mCallingActivity;
    private GoogleMap mMap;
    private int mExtensionId;
    private Marker mPrevMarker;
    private double mPrevLatitude;
    private double mPrevLongitude;


    public static AttachGeoLocMapView getInstance() {
        return new AttachGeoLocMapView();
    }

    public final void initializeView(FragmentActivity callingActivity, int extensionId,
                                     double prevLatitude, double prevLongitude) {
        mExtensionId = extensionId;
        mPrevLatitude = prevLatitude;
        mPrevLongitude = prevLongitude;
        AttachGeoLocPresenter.setGeoLocation(mExtensionId, mPrevLatitude, mPrevLongitude);
        mCallingActivity = callingActivity;
        mCallingActivity.getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                this, AttachGeoLocMapView.class.getSimpleName()).commit();

        // Chequeo si el mapa esta instanciado o no.
        if (mMap == null) {
            // Se obtiene el mapa a partir del SupportMapFragment.
            getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    setUpMap();
                }
            });
        }

    }

    private void setUpMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        MarkerOptions prevMarker = new MarkerOptions();
        LatLng prevPosition = new LatLng(mPrevLatitude, mPrevLongitude);
        prevMarker.position(prevPosition);
        mPrevMarker = mMap.addMarker(prevMarker);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions newMarkerOpt = new MarkerOptions();
        newMarkerOpt.position(latLng);
        mPrevMarker.remove();
        mPrevMarker = mMap.addMarker(newMarkerOpt);
        AttachGeoLocPresenter.setGeoLocation(mExtensionId, mPrevMarker.getPosition().latitude,
                mPrevMarker.getPosition().longitude);
        return;
    }

    private void successAttach() {
        Toast.makeText(this.getContext(), "Succes!!", Toast.LENGTH_SHORT).show();
    }
}
