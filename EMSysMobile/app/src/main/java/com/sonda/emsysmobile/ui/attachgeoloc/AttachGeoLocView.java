package com.sonda.emsysmobile.ui.attachgeoloc;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class AttachGeoLocView extends SupportMapFragment
        implements GoogleMap.OnMapClickListener {

    private CustomScrollView mMainScrollView;
    private FragmentActivity mCallingActivity;
    private GoogleMap mMap;
    private int mExtensionId;
    private Marker mPrevMarker;
    private Marker mNewMarker;
    private double mPrevLatitude;
    private double mPrevLongitude;


    public static AttachGeoLocView getInstance() {
        return new AttachGeoLocView();
    }

    public final void initializeView(FragmentActivity callingActivity,
                                     CustomScrollView mainScrollView,
                                     int extensionId, double prevLatitude, double prevLongitude) {
        mExtensionId = extensionId;
        mPrevLatitude = prevLatitude;
        mPrevLongitude = prevLongitude;
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
        return;
    }

    private void updateGeoLocation(double latitude, double longitude) {
        String user = "";
        Date date = new Date();
        GeolocationDto geoLocationDto =
                new GeolocationDto(mExtensionId, user, date, latitude, longitude);
        UpdateGeoLocationRequest<UpdateGeoLocationResponse> request =
                new UpdateGeoLocationRequest<>(
                        this.getContext(), GeolocationDto.class, geoLocationDto);
        request.setListener(new Response.Listener<UpdateGeoLocationResponse>() {
            @Override
            public void onResponse(UpdateGeoLocationResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    //TODO no se que hacer cuando el llamado es exitoso
                    successAttach();
                } else {
                    //TODO no se que hacer cuando falla
                }
            }
        });
        request.execute();
    }

    private void successAttach() {
        Toast.makeText(this.getContext(), "Succes!!", Toast.LENGTH_SHORT).show();
    }
}
