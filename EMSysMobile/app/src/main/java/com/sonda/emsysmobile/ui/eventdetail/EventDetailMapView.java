package com.sonda.emsysmobile.ui.eventdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.sonda.emsysmobile.ui.changeview.CustomMarkerData;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.MapUtils.areBoundsTooSmall;

/**
 * Created by marccio on 11-Oct-16.
 */

public class EventDetailMapView extends SupportMapFragment
        implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private static final float DEFAULT_ZOOM = 14;
    private static final LatLng DEFAULT_LAT_LONG =
            new LatLng(-34.9021945, -56.1644537);
    private static final String TAG = EventDetailMapView.class.getName();
    private GoogleMap mMap;
    private List<List<CustomMarkerData>> mMarkerDataList;
    private List<Marker> mMarkers = new ArrayList<>();

    public static EventDetailMapView getInstance() {
        return new EventDetailMapView();
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (mMap == null) {
            // Se obtiene el mapa a partir del SupportMapFragment.
            getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(DEFAULT_LAT_LONG, DEFAULT_ZOOM));
                    updateView();
                }
            });
        }
    }

    public final void updateView() {
        EventDetailMapPresenter.loadEventDetails(getActivity(), this);
    }

    /**
     * Llamado por el presenter, desencadena la actualizacion de la vista.
     *
     * @param markerDataList
     */
    public final void updateEventData(List<List<CustomMarkerData>> markerDataList) {
        mMarkerDataList = markerDataList;
        setUpMap();
    }

    private void setUpMap() {
        Log.d(TAG, "Map obtained");
        mMap.getUiSettings().setZoomControlsEnabled(true);
        addMarkersToMap();
        // Info window adapter por si se quiere customizar la info window.
        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        // Listeners para eventos sobre los marcadores del mapa.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        // Se hace zoom para que todos los marcadores queden en vista.
        final View mapView = getView();
        if (mapView != null && mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onGlobalLayout() {
                            if (!mMarkerDataList.isEmpty()) {
                                LatLngBounds.Builder bld = new LatLngBounds.Builder();
                                for (List<CustomMarkerData> list : mMarkerDataList) {
                                    for (CustomMarkerData markers : list) {
                                        bld.include(markers.getCoordinates());
                                    }
                                }
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
                        }
                    });
        }
    }

    private void addMarkersToMap() {
        mMap.clear();
        mMarkers.clear();
        List<Float> availableColors = getAvailableColors();
        for (List<CustomMarkerData> list : mMarkerDataList) {
            float currentColor = availableColors.get(0);
            for (CustomMarkerData markerData : list) {
                // Se reajustan coordenadas si hay colisiones, ya que la API
                // de Google Maps no soporta marcadores que colisionen.
                BitmapDescriptor bitmapMarker;
                bitmapMarker = BitmapDescriptorFactory.defaultMarker(currentColor);
                Log.i(TAG, "Default marker (red)");
                MarkerOptions markerOptions = new MarkerOptions().position(
                        markerData.getCoordinates()).title(markerData.getTitle())
                        .icon(bitmapMarker);
                Marker marker = mMap.addMarker(markerOptions);
                mMarkers.add(marker);
                Log.i(TAG, "Se agregó un marcador");
                Log.d(TAG, "Tamaño de mMarkers = " + mMarkers.size());
            }
            availableColors.remove(currentColor);
            if (availableColors.size() == 0) {
                // Se reinician los colores. Se pueden repetir si hay muchas extensiones.
                availableColors = getAvailableColors();
            }
        }
    }

    private List<Float> getAvailableColors() {
        List<Float> availableColors = new ArrayList<>();
        availableColors.add(BitmapDescriptorFactory.HUE_RED);
        availableColors.add(BitmapDescriptorFactory.HUE_AZURE);
        availableColors.add(BitmapDescriptorFactory.HUE_BLUE);
        availableColors.add(BitmapDescriptorFactory.HUE_CYAN);
        availableColors.add(BitmapDescriptorFactory.HUE_ORANGE);
        availableColors.add(BitmapDescriptorFactory.HUE_GREEN);
        availableColors.add(BitmapDescriptorFactory.HUE_ROSE);
        availableColors.add(BitmapDescriptorFactory.HUE_VIOLET);
        availableColors.add(BitmapDescriptorFactory.HUE_MAGENTA);
        availableColors.add(BitmapDescriptorFactory.HUE_YELLOW);
        return availableColors;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
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
