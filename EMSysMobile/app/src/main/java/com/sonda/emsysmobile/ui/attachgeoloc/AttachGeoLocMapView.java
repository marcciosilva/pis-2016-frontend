package com.sonda.emsysmobile.ui.attachgeoloc;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sonda.emsysmobile.R;

import static com.sonda.emsysmobile.utils.MapUtils.areBoundsTooSmall;

/**
 * Created by Pape on 10/24/2016.
 */

public class AttachGeoLocMapView extends SupportMapFragment
        implements GoogleMap.OnMapClickListener {

    private FragmentActivity mCallingActivity;
    private GoogleMap mMap;
    private int mExtensionId;
    private Marker mCurrentMarker;
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
        mCurrentMarker = mMap.addMarker(prevMarker);
        updateZoomLevel();
    }

    private void updateZoomLevel() {
        // Se hace zoom para que todos los marcadores queden en vista.
        final View mapView = getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onGlobalLayout() {
                            LatLngBounds.Builder bld = new LatLngBounds.Builder();
                            bld.include(mCurrentMarker.getPosition());
                            LatLngBounds bounds = bld.build();
                            final int minDistanceInMeter = 600;
                            if (areBoundsTooSmall(bounds, minDistanceInMeter)) {
                                final int v = 17;
                                mMap.animateCamera(CameraUpdateFactory
                                        .newLatLngZoom(bounds.getCenter(), v));
                            } else {
                                final int i = 70;
                                mMap.animateCamera(CameraUpdateFactory
                                        .newLatLngBounds(bounds, i));
                            }
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                    });
        }
    }

    @Override
    public final void onMapClick(LatLng latLng) {
        MarkerOptions newMarkerOpt = new MarkerOptions();
        newMarkerOpt.position(latLng);
        mCurrentMarker.remove();
        mCurrentMarker = mMap.addMarker(newMarkerOpt);
        AttachGeoLocPresenter.setGeoLocation(mExtensionId, mCurrentMarker.getPosition().latitude,
                mCurrentMarker.getPosition().longitude);
        return;
    }

    private void successAttach() {
        Toast.makeText(this.getContext(), "Succes!!", Toast.LENGTH_SHORT).show();
    }
}
