package com.sonda.emsysmobile.ui.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.fragments.ExtensionsFragment;
import com.sonda.emsysmobile.ui.views.CustomScrollView;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

public class HomeActivity extends AppCompatActivity
        implements ExtensionsFragment.OnListFragmentInteractionListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener{

    private static final String TAG = HomeActivity.class.getName();
    private SupportMapFragment mMapFragment = null;
    private GoogleMap mMap;
    private CustomScrollView mMainScrollView;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ExtensionsFragment extensionsFragment = new ExtensionsFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, extensionsFragment).commit();
            // Inicializacion de fragment de mapa.
            initMapFragment();
            mMainScrollView = (CustomScrollView) findViewById(R.id.main_scrollview);
        }
    }

    private void initMapFragment() {
        mMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_container,
                mMapFragment, SupportMapFragment.class.getSimpleName()).commit();
        // Chequeo si el mapa esta instanciado o no.
        if (mMap == null) {
            // Se obtiene el mapa a partir del SupportMapFragment.
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                }
            });
        }
        // Inicializacion de ubicaciones de prueba.
        TestLocation location1 = new TestLocation(-34.905743, -56.198887);
        TestLocation location2 = new TestLocation(-35.905743, -56.198887);
        testLocations.add(location1);
        testLocations.add(location2);
        hideMapFragment();
    }

    public class TestLocation {

        private double latitude;
        private double longitude;

        public TestLocation(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
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
    }

    private List<TestLocation> testLocations = new ArrayList<>();

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
        final View mapView = mMapFragment.getView();
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

    private List<Marker> mMarkers = new ArrayList<>();

    private void addMarkersToMap() {
        mMap.clear();
        mMarkers.clear();
        for (TestLocation testLocation : testLocations) {
            LatLng ll = new LatLng(testLocation.getLatitude(), testLocation.getLongitude());
            BitmapDescriptor bitmapMarker;
            bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            Log.i(TAG, "Default marker (red)");
            mMarkers.add(mMap.addMarker(new MarkerOptions().position(ll).title("Marcador de prueba")
                    .snippet("Estado de prueba").icon(bitmapMarker)));
            Log.i(TAG,"Se agregó un marcador");
            Log.d(TAG, "Tamaño de mMarkers = " + mMarkers.size());
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DialogFragment dialog = UIUtils.getSimpleDialog("Debe cerrar sesión para modificar su rol.");
        dialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto item) {
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        String textString = "text";
        if (item.getItemId() != R.id.menu_view_map_button) {
            hideMapFragment();
        }
        switch (item.getItemId()) {
            case R.id.menu_create_event_button:
                Fragment fragment = new TestFragment();
                Bundle args = new Bundle();
                args.putString(textString, getString(R.string.menu_create_event_string));
                fragment.setArguments(args);
                replaceFragment(fragment, "fragment1");
                return true;
            case R.id.menu_list_events_button:
                ExtensionsFragment extensionsFragment = (ExtensionsFragment) getSupportFragmentManager().findFragmentByTag(ExtensionsFragment.class.getSimpleName());
                if (extensionsFragment == null) {
                    extensionsFragment = new ExtensionsFragment();
                    replaceFragment(extensionsFragment, ExtensionsFragment.class.getSimpleName());
                }
                return true;
            case R.id.menu_external_service_button:
                fragment = new TestFragment();
                args = new Bundle();
                args.putString(textString, getString(R.string.menu_external_service_string));
                fragment.setArguments(args);
                replaceFragment(fragment, "fragment2");
                return true;
            case R.id.menu_view_map_button:
                showMapFragment();
                extensionsFragment = (ExtensionsFragment) getSupportFragmentManager()
                        .findFragmentByTag(ExtensionsFragment.class.getSimpleName());
                if (extensionsFragment == null) {
                    extensionsFragment = new ExtensionsFragment();
                    replaceFragment(extensionsFragment, ExtensionsFragment.class.getSimpleName());
                }
                return true;
            case R.id.menu_logout_button:
                logout();
                return true;
            default:
                // Accion no reconocida, se lo delega a la superclase.
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMapFragment() {
        try {
            ViewGroup.LayoutParams mapParams = mMapFragment.getView().getLayoutParams();
            if (mapParams != null) {
                mapParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                mMapFragment.getView().setLayoutParams(mapParams);
            }
            mMainScrollView.addInterceptScrollView(mMapFragment.getView());
        } catch (NullPointerException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
        getSupportFragmentManager().beginTransaction().show(mMapFragment).commitNow();
        // Se configura el mapa para tener marcadores.
        setUpMap();
    }

    private void hideMapFragment() {
        try {
            ViewGroup.LayoutParams mapParams = mMapFragment.getView().getLayoutParams();
            if (mapParams != null) {
                mapParams.height = 0;
                mMapFragment.getView().setLayoutParams(mapParams);
            }
            mMainScrollView.removeInterceptScrollView(mMapFragment.getView());
        } catch (NullPointerException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
        if (mMapFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(mMapFragment).commit();
        }
    }

    private void replaceFragment(Fragment fragment, String fragmentTAG) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragmentTAG).commit();
    }

    private void logout() {
        LogoutRequest<LoginLogoutResponse> request = new LogoutRequest<>(getApplicationContext(), LoginLogoutResponse.class);
        request.setListener(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    // Se reinicia el token de autenticacion.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", "").commit();
                    goToSplash();
                } else {
                    String errorMsg = response.getInnerResponse().getMsg();
                    handleErrorMessage(HomeActivity.this, responseCode, errorMsg);
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
                handleVolleyErrorResponse(HomeActivity.this, error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
            }
        });
        request.execute();
    }

    private void goToSplash() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

/**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class TestFragment extends Fragment {

        public TestFragment() {
            // Constructor vacio requerido por todas las subclases de Fragment.
        }

        @Override
        public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.test_fragment_layout, container, false);
            String text = getArguments().getString("text");
            TextView textView = (TextView) rootView.findViewById(R.id.fragment_textview);
            textView.setText(text);
            return rootView;
        }
    }

}