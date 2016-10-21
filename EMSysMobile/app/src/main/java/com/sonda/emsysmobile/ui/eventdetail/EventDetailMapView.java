package com.sonda.emsysmobile.ui.eventdetail;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

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
import com.sonda.emsysmobile.ui.changeview.CustomMarkerData;
import com.sonda.emsysmobile.ui.views.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.MapUtils.areBoundsTooSmall;

/**
 * Created by marccio on 11-Oct-16.
 */

public class EventDetailMapView extends SupportMapFragment
        implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private FragmentActivity mCallingActivity;
    private GoogleMap mMap;
    private static final String TAG = EventDetailMapView.class.getName();
    private List<List<CustomMarkerData>> mMarkerDataList;
    private List<Marker> mMarkers = new ArrayList<>();
    private CustomScrollView mMainScrollView;
    private boolean mShouldBeVisible = false;

    public static EventDetailMapView getInstance() {
        return new EventDetailMapView();
    }

    public void initializeView(FragmentActivity callingActivity, CustomScrollView mainScrollView) {
        mMainScrollView = mainScrollView;
        mCallingActivity = callingActivity;
        mCallingActivity.getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                this, EventDetailMapView.class.getSimpleName()).commit();
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

    private void loadEventData() {
        EventDetailMapPresenter.loadEventDetails(mCallingActivity, this);
    }

    /**
     * Llamado por el presenter, desencadena la actualizacion de la vista.
     *
     * @param markerDataList
     */
    public void updateEventData(List<List<CustomMarkerData>> markerDataList) {
        mMarkerDataList = markerDataList;
        if (mShouldBeVisible) {
            updateView();
        }
    }

    public void hideView() {
        try {
            mShouldBeVisible = false;
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
        Log.d(TAG, "updateView llamado");
        try {
            View view = getView();
            ViewGroup.LayoutParams mapParams = view.getLayoutParams();
            if (mapParams != null) {
                mapParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        200, getResources().getDisplayMetrics());
                //mapParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                //800, getResources().getDisplayMetrics());
                view.setLayoutParams(mapParams);
            }
            mMainScrollView.addInterceptScrollView(view);
            mCallingActivity.getSupportFragmentManager().beginTransaction().show(this).commitNow();
            // Se encarga de scrollear hasta el tope de la activity una vez que el mapa
            // este cargado.
            final ScrollView scrollview = (ScrollView) mCallingActivity.findViewById(R.id.main_scrollview);
            if (scrollview != null) {
                scrollview.fullScroll(ScrollView.FOCUS_UP);
            }
            // Se configura el mapa para tener marcadores.
            setUpMap();
        } catch (NullPointerException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
    }

    public void showView() {
        mShouldBeVisible = true;
        loadEventData();
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
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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
                        if (areBoundsTooSmall(bounds, 600)) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 17));
                        } else {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
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
