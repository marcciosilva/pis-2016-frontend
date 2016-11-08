package com.sonda.emsysmobile.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.backendcommunication.services.KeepAliveService;
import com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.managers.MultimediaManager;
import com.sonda.emsysmobile.managers.NotificationsManager;
import com.sonda.emsysmobile.notifications.MyFirebaseInstanceIDService;
import com.sonda.emsysmobile.ui.changeview.EventsMapView;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsPresenter;
import com.sonda.emsysmobile.ui.extensions.ExtensionsListFragment;
import com.sonda.emsysmobile.ui.fragments.ExternalServiceQueryFragment;
import com.sonda.emsysmobile.ui.fragments.MapExtensionsFragment;
import com.sonda.emsysmobile.ui.fragments.NotificationsFragment;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.dialogs.EventFilterDialogFragment;
import com.sonda.emsysmobile.utils.UIUtils;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

public class HomeActivity extends RootActivity
        implements OnListFragmentInteractionListener,
        EventFilterDialogFragment.OnEventFilterDialogListener,
        NotificationsFragment.OnFragmentInteractionListener {

    public static final String TAG = HomeActivity.class.getName();

    private EventsMapView mMapView;
    private FrameLayout mMapContainer;
    private FrameLayout mFragmentsContainer;
    private FloatingActionButton mFloatingButton;
    private boolean mContainerCollapsed;
    private MapExtensionsFragment mMapExtensionsFragment;
    private String mSelectedFilter = "Prioridad";

    @Override
    public final void onEventFilter(String selectedFilter) {
        UIUtils.hideSoftKeyboard(this);
        mSelectedFilter = selectedFilter;
        if (mMapContainer.getVisibility() == View.VISIBLE){
            mMapExtensionsFragment.setFilter(selectedFilter);
            mMapExtensionsFragment.getMapEvents();
        }else {
            ExtensionsListFragment extensionsListFragment = (ExtensionsListFragment) getSupportFragmentManager()
                    .findFragmentByTag(ExtensionsListFragment.class.getSimpleName());
            if (extensionsListFragment != null) {
                extensionsListFragment.setFilter(selectedFilter);
                extensionsListFragment.loadData(false);
            }
        }
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_home, R.id.activity_main_layout, "Listado de eventos", RootActivity.EVENT_LIST);

        // Start KeepAlive service.
        Intent intent = new Intent(HomeActivity.this, KeepAliveService.class);
        startService(intent);

        // Creating notifications manager singleton
        NotificationsManager.getInstance(this);

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
            ExtensionsListFragment mExtensionsFragment = new ExtensionsListFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            mFragmentsContainer = (FrameLayout) findViewById(R.id.fragment_container);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mExtensionsFragment).commit();

            //Crear fragment para lista en mapa
            mMapExtensionsFragment = new MapExtensionsFragment();

            mMapContainer = (FrameLayout) findViewById(R.id.map_container);
        }

        mFloatingButton = (FloatingActionButton) findViewById(R.id.floating_button);
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFragmentContainer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.embedded, menu);
            menu.findItem(R.id.menu_1).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_filter_list).color(Color.WHITE).actionBar());
            menu.findItem(R.id.menu_2).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_notifications_active).color(Color.WHITE).actionBar());
        return true;
    }


    public final void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public final void onListFragmentInteraction(ExtensionDto extension) {
        try {
            String eventIdString = Integer.toString(extension.getEvent().getIdentifier());
            if (eventIdString == null) {
                throw (new NullPointerException("EVENT_ID resulta nulo."));
            }
            String eventExtensionZone = Integer.toString(extension.getIdentifier());
            if (eventExtensionZone == null) {
                throw (new NullPointerException("EVENT_EXTENSION_ID resulta nulo."));
            }
            EventDetailsPresenter.loadEventDetails(HomeActivity.this, extension.getEvent()
                    .getIdentifier(), extension.getIdentifier());
        } catch (NullPointerException e) {
            if (!isFinishing()) {
                UIUtils.handleErrorMessage(this, ErrorCodeCategory.LOGIC_ERROR.getNumVal(),
                        getString(R.string.error_internal));
            }
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_1){
            showMapView(false);
            // Primero se redirige al listado.
            ExtensionsListFragment extensionsListFragment = (ExtensionsListFragment) getSupportFragmentManager()
                    .findFragmentByTag(ExtensionsListFragment.class.getSimpleName());
            if (extensionsListFragment == null) {
                extensionsListFragment = new ExtensionsListFragment();
            }
            replaceFragment(extensionsListFragment, ExtensionsListFragment.class.getSimpleName());
            // Luego se abre el diálogo para elegir el filtro.
            FragmentManager fm = getSupportFragmentManager();
            EventFilterDialogFragment eventFilterDialogFragment = EventFilterDialogFragment.newInstance();
            eventFilterDialogFragment.show(fm, eventFilterDialogFragment.getClass().getSimpleName());
        } else if (item.getItemId() == R.id.menu_2) {
            showMapView(false);

            NotificationsFragment notificationsFragment = NotificationsFragment.newInstance();
            replaceFragment(notificationsFragment, NotificationsFragment.class.getSimpleName());
        }

        return true;
    }

    @Override
    protected void goToEventCreateView() {
        getSupportActionBar().setTitle("Creación de evento");
        showFilterMenu(false);
        showMapView(false);
        Fragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString("text", getString(R.string.menu_create_event_string));
        fragment.setArguments(args);
        replaceFragment(fragment, "fragment1");
    }

    @Override
    protected void goToEventListView() {
        getSupportActionBar().setTitle("Listado de eventos");
        showFilterMenu(true);
        showMapView(false);
        ExtensionsListFragment extensionsFragment = (ExtensionsListFragment) getSupportFragmentManager()
                .findFragmentByTag(ExtensionsListFragment.class.getSimpleName());
        if (extensionsFragment == null) {
            extensionsFragment = new ExtensionsListFragment();
        }
        replaceFragment(extensionsFragment, ExtensionsListFragment.class.getSimpleName());
    }

    @Override
    protected void goToEventMapView() {
        getSupportActionBar().setTitle("Eventos en el mapa");
        showFilterMenu(false);
        showMapView(true);
        if (mMapView == null) {
            mMapView = EventsMapView.getInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.map_container, mMapView, EventsMapView.class.getSimpleName()).commit();
        } else {
            mMapView.updateView();
        }
        mMapExtensionsFragment = (MapExtensionsFragment) getSupportFragmentManager()
                .findFragmentByTag(MapExtensionsFragment.class.getSimpleName());
        if (mMapExtensionsFragment == null) {
            mMapExtensionsFragment = new MapExtensionsFragment();
        }
        replaceFragment(mMapExtensionsFragment, MapExtensionsFragment.class.getSimpleName());
    }

    @Override
    protected void goToExternalServiceView() {
        getSupportActionBar().setTitle("Servicio externo");
        showFilterMenu(false);
        showMapView(false);
        ExternalServiceQueryFragment externalServiceFragment = (ExternalServiceQueryFragment) getSupportFragmentManager()
                .findFragmentByTag(ExternalServiceQueryFragment.class.getSimpleName());
        if (externalServiceFragment == null) {
            externalServiceFragment = new ExternalServiceQueryFragment();
        }
        replaceFragment(externalServiceFragment, ExternalServiceQueryFragment.class.getSimpleName());
    }

    private void replaceFragment(Fragment fragment, String fragmentTAG) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTAG).commit();
    }

    private void showMapView(boolean visible) {
        if (visible) {
            mMapContainer.setVisibility(View.VISIBLE);
            mFloatingButton.setVisibility(View.VISIBLE);
            mContainerCollapsed = false;
            mFloatingButton.setImageDrawable(ContextCompat
                    .getDrawable(this, R.drawable.ic_keyboard_arrow_down_white_24dp));
        } else {
            mMapContainer.setVisibility(View.GONE);
            mFloatingButton.setVisibility(View.GONE);
            mFragmentsContainer.setVisibility(View.VISIBLE);
        }
    }

    private void showFilterMenu(boolean visible){
        if (visible) {
            findViewById(R.id.menu_1).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.menu_1).setVisibility(View.GONE);
        }
    }

    private void showNotificationIcon(boolean visible) {
        if (visible) {
            findViewById(R.id.menu_2).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.menu_2).setVisibility(View.GONE);
        }
    }

    private void toggleFragmentContainer() {
        if (mContainerCollapsed) {
            mFragmentsContainer.setVisibility(View.VISIBLE);
            mFloatingButton.setImageDrawable(ContextCompat
                    .getDrawable(this, R.drawable.ic_keyboard_arrow_down_white_24dp));
        } else {
            mFragmentsContainer.setVisibility(View.GONE);
            mFloatingButton.setImageDrawable(ContextCompat
                    .getDrawable(this, R.drawable.ic_keyboard_arrow_up_white_24dp));
        }
        mContainerCollapsed = !mContainerCollapsed;
    }

    private void logout() {
        // Se borran los datos del usuario.
        GlobalVariables.setUserData(null);
        LogoutRequest<LoginLogoutResponse> request =
                new LogoutRequest<>(getApplicationContext(), LoginLogoutResponse.class);
        request.setListener(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    // Stop KeepAlive service.
                    Intent intent = new Intent(HomeActivity.this, KeepAliveService.class);
                    stopService(intent);
                    // Se reinicia el token de autenticacion.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit()
                            .putString("access_token", "").commit();
                    // Se reinicia el token de notificaciones
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    sharedPrefs.edit().remove(MyFirebaseInstanceIDService.NOTIFICATION_TOKEN_KEY).apply();
                    EventManager.getInstance(HomeActivity.this).onLogout();
                    goToSplash();
                } else {
                    String errorMsg = response.getInnerResponse().getMsg();
                    if (!isFinishing()) {
                        handleErrorMessage(HomeActivity.this, responseCode, errorMsg);
                    }
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
                if (!isFinishing()) {
                    handleVolleyErrorResponse(HomeActivity.this, error, new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                        }
                    });
                }
            }
        });
        request.execute();
    }

    private void goToSplash() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNotificationSelected(int eventId) {

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