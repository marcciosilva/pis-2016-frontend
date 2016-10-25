package com.sonda.emsysmobile.ui.activities.attachgeoloc;

import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.changeview.EventsMapView;
import com.sonda.emsysmobile.ui.views.CustomScrollView;

/**
 * Created by Pape on 10/24/2016.
 */

public class AttachGeoLocView extends SupportMapFragment
        implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private CustomScrollView mMainScrollView;
    private GoogleMap mMap;
    private FragmentActivity mCallingActivity;

    public static AttachGeoLocView getInstance() {
        return new AttachGeoLocView();
    }

    public final void initializeView(FragmentActivity callingActivity, CustomScrollView mainScrollView) {
        mMainScrollView = mainScrollView;
        mCallingActivity = callingActivity;
        mCallingActivity.getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                this, AttachGeoLocView.class.getSimpleName()).commit();

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

    @Override
    public final void onInfoWindowClick(Marker marker) {
        // Se pasa al presenter la informacion del marcador, en el tipo de datos
        // custom utilizado para ellos (CustomMarkerData).
//        boolean successfulOperation = EventsMapPresenter
//                .showEventDetail(mCallingActivity, new CustomMarkerData(marker.getTitle(),
//                        marker.getSnippet(), marker.getPosition()));
        // Si no se pudo completar la operacion de mostrar el detalle del evento,
        // se presenta un dialog informando al usuario acerca de ello.
//        if (!successfulOperation) {
//            DialogFragment dialog =
//                    UIUtils.getSimpleDialog(getString(R.string.error_event_details_from_map));
//            dialog.show(mCallingActivity.getSupportFragmentManager(), TAG);
//        }
    }

}
