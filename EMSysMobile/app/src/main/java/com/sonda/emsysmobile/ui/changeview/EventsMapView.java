package com.sonda.emsysmobile.ui.changeview;

import android.annotation.SuppressLint;
import android.support.v4.app.DialogFragment;
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
import com.sonda.emsysmobile.ui.views.CustomScrollView;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.MapUtils.areBoundsTooSmall;

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
    private boolean mShouldBeVisible = false;

    public static EventsMapView getInstance() {
        return new EventsMapView();
    }

    public final void initializeView(FragmentActivity callingActivity, CustomScrollView mainScrollView) {
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
    public final void updateEventsData(List<CustomMarkerData> markerDataList) {
        mMarkerDataList = markerDataList;
        if (mShouldBeVisible) {
            updateView();
        }
    }

    public final void hideView() {
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
        try {
            View view = getView();
            ViewGroup.LayoutParams mapParams = view.getLayoutParams();
            if (mapParams != null) {
                final int value = 200;
                mapParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        value, getResources().getDisplayMetrics());
                view.setLayoutParams(mapParams);
            }
            mMainScrollView.addInterceptScrollView(view);
            mCallingActivity.getSupportFragmentManager().beginTransaction().show(this).commitNow();
            // Se encarga de scrollear hasta el tope de la activity una vez que el mapa
            // este cargado.
            final ScrollView scrollview =
                    (ScrollView) mCallingActivity.findViewById(R.id.main_scrollview);
            if (scrollview != null) {
                scrollview.fullScroll(ScrollView.FOCUS_UP);
            }
            // Se configura el mapa para tener marcadores.
            setUpMap();
        } catch (NullPointerException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
    }

    public final void showView() {
        mShouldBeVisible = true;
        loadEventsData();
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
            mapView.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onGlobalLayout() {
                            if (!mMarkerDataList.isEmpty()) {
                                LatLngBounds.Builder bld = new LatLngBounds.Builder();
                                for (CustomMarkerData event : mMarkerDataList) {
                                    bld.include(event.getCoordinates());
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
    public final void onInfoWindowClick(Marker marker) {
        // Se pasa al presenter la informacion del marcador, en el tipo de datos
        // custom utilizado para ellos (CustomMarkerData).
        boolean successfulOperation = EventsMapPresenter
                .showEventDetail(mCallingActivity, new CustomMarkerData(marker.getTitle(),
                        marker.getSnippet(), marker.getPosition()));
        // Si no se pudo completar la operacion de mostrar el detalle del evento,
        // se presenta un dialog informando al usuario acerca de ello.
        if (!successfulOperation) {
            DialogFragment dialog =
                    UIUtils.getSimpleDialog(getString(R.string.error_event_details_from_map));
            dialog.show(mCallingActivity.getSupportFragmentManager(), TAG);
        }
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
