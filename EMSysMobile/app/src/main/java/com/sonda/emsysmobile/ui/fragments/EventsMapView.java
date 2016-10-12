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

public class EventsMapView extends SupportMapFragment
        implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private FragmentActivity mCallingActivity;
    private GoogleMap mMap;
    private static final String TAG = EventsMapView.class.getName();
    private List<CustomMarkerData> mMarkerDataList;
    private List<Marker> mMarkers = new ArrayList<>();
    private CustomScrollView mMainScrollView;

    public static EventsMapView getInstance() {
        return new EventsMapView();
    }

    public void initializeView(FragmentActivity callingActivity, CustomScrollView mainScrollView) {
        mMainScrollView = mainScrollView;
        mCallingActivity = callingActivity;
        mCallingActivity.getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                this, EventsMapView.class.getSimpleName()).commit();
        // Chequeo si el mapa esta instanciado o no.
        if (mMap == null) {
            // Se obtiene el mapa a partir del SupportMapFragment.
            getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                }
            });
        }
        hideView();
    }

    private void loadEventsData() {
        EventsMapPresenter.loadEvents(mCallingActivity, this);
    }

    /**
     * Llamado por el presenter, desencadena la actualizacion de la vista.
     *
     * @param markerDataList
     */
    public void updateEventsData(List<CustomMarkerData> markerDataList) {
        mMarkerDataList = markerDataList;
        updateView();
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

    private void updateView() {
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

    public void showView() {
        loadEventsData();
    }

    private void setUpMap() {
        Log.d(TAG, "Map obtained");
        mMap.getUiSettings().setZoomControlsEnabled(false);
        addMarkersToMap();
        // Info window adapter por si se quiere customizar la info window.
        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        // Listeners para eventos sobre los marcadores del mapa.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        // Se hace zoom para que todos los marcadores queden en vista.
        final View mapView = getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressLint("NewApi")
                @Override
                public void onGlobalLayout() {
                    LatLngBounds.Builder bld = new LatLngBounds.Builder();
                    for (CustomMarkerData event : mMarkerDataList) {
                        bld.include(event.getCoordinates());
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
        for (CustomMarkerData markerData : mMarkerDataList) {
            // Se reajustan coordenadas si hay colisiones, ya que la API
            // de Google Maps no soporta marcadores que colisionen.
            BitmapDescriptor bitmapMarker;
            bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            Log.i(TAG, "Default marker (red)");
            MarkerOptions markerOptions = new MarkerOptions().position(
                    markerData.getCoordinates()).title(markerData.getTitle())
                    .snippet(markerData.getDescription())
                    .icon(bitmapMarker);
            Marker marker = mMap.addMarker(markerOptions);
            mMarkers.add(marker);
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
        //DialogFragment dialog = UIUtils.getExtensionMapMarkerDialog("Faggot");
        //dialog.show(mCallingActivity.getSupportFragmentManager(), TAG);
        //return true;
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
