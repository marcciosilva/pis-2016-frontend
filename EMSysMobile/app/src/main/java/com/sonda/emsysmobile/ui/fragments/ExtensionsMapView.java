package com.sonda.emsysmobile.ui.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.views.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marccio on 11-Oct-16.
 */

public class ExtensionsMapView extends SupportMapFragment
        implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private FragmentActivity mCallingActivity;
    private GoogleMap mMap;
    private static final String TAG = ExtensionsMapView.class.getName();
    private List<TestLocation> testLocations = new ArrayList<>();
    private List<Marker> mMarkers = new ArrayList<>();
    private CustomScrollView mMainScrollView;

    public class TestLocation {

        private double latitude;
        private double longitude;
        private String id;

        public TestLocation(double latitude, double longitude, String id) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static ExtensionsMapView getInstance() {
        return new ExtensionsMapView();
    }

    public void initializeView(FragmentActivity callingActivity, CustomScrollView mainScrollView) {
        mMainScrollView = mainScrollView;
        mCallingActivity = callingActivity;
        mCallingActivity.getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                this, ExtensionsMapView.class.getSimpleName()).commit();
        // Chequeo si el mapa esta instanciado o no.
        if (mMap == null) {
            // Se obtiene el mapa a partir del SupportMapFragment.
            getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    // Set onClick listener configured for spiderfication:
//                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                        @Override
//                        public boolean onMarkerClick (Marker marker){
//                            // We need to figure out if it was a seperate marker or a cluster marker
//                            if (marker.isCluster()) {
//                                if (mMap.getCameraPosition().zoom >= 15) //Play around with this. We assume the SPIDERFICATION_ZOOM_THRSH is constant and never changes.
//                                    oms.spiderListener(marker); // That's where the magic happens
//                                else {
//                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                            marker.getPosition(),
//                                            mMap.getCameraPosition().zoom + dynamicZoomLevel()));
//                                    updateClusteringRadius();
//                                }
//                                return true;
//                            }
//                            return false;
//                        }
//                    });
                }
            });
        }
        // Inicializacion de ubicaciones de prueba.
        TestLocation location1 = new TestLocation(-34.905743, -56.198887, "Tu vieja");
        TestLocation location2 = new TestLocation(-35.905743, -56.198887, "Bony");
        TestLocation location3 = new TestLocation(-35.905743, -56.198887, "Pirulo247");
        testLocations.add(location1);
        testLocations.add(location2);
        testLocations.add(location3);
        hideView();
    }

    public void hideView() {
        try {
            View view = getView();
            ViewGroup.LayoutParams mapParams = view.getLayoutParams();
            if (mapParams != null) {
                mapParams.height = 0;
                getView().setLayoutParams(mapParams);
            }
            mMainScrollView.removeInterceptScrollView(view);
        } catch (NullPointerException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
        mCallingActivity.getSupportFragmentManager().beginTransaction().hide(this).commit();
    }

    public void showView() {
        try {
            View view = getView();
            ViewGroup.LayoutParams mapParams = view.getLayoutParams();
            if (mapParams != null) {
                mapParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                view.setLayoutParams(mapParams);
            }
            mMainScrollView.addInterceptScrollView(view);
            mCallingActivity.getSupportFragmentManager().beginTransaction().show(this).commitNow();
            // Se configura el mapa para tener marcadores.
            setUpMap();
        } catch (NullPointerException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
    }

    private void setUpMap() {
        Log.d(TAG, "Map obtained");
        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);
        // Add lots of markers to the map.
        addMarkersToMap();
        // Setting an info window adapter allows us to change the both the
        // contents and look of the
        // info window.
//        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        // Set listeners for marker events. See the bottom of this class for
        // their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        // Pan to see all markers in view.
        // Cannot zoom to bounds until the map has a size.
        final View mapView = getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressLint("NewApi")
                // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    LatLngBounds.Builder bld = new LatLngBounds.Builder();
                    for (TestLocation testLocation : testLocations) {
                        LatLng ll = new LatLng(testLocation.getLatitude(), testLocation.getLongitude());
                        bld.include(ll);
                    }
                    LatLngBounds bounds = bld.build();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
                    mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }

    private void addMarkersToMap() {
        mMap.clear();
        mMarkers.clear();
        for (TestLocation testLocation : testLocations) {
            LatLng ll = new LatLng(testLocation.getLatitude(), testLocation.getLongitude());
            BitmapDescriptor bitmapMarker;
            bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            Log.i(TAG, "Default marker (red)");
            MarkerOptions markerOptions = new MarkerOptions().position(ll).title(testLocation.getId())
                    .snippet("Estado de prueba").icon(bitmapMarker);
            mMarkers.add(mMap.addMarker(markerOptions));
            Log.i(TAG, "Se agregó un marcador");
            Log.d(TAG, "Tamaño de mMarkers = " + mMarkers.size());
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

}
