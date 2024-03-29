package com.sonda.emsysmobile.ui.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.services.KeepAliveService;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.managers.MultimediaManager;
import com.sonda.emsysmobile.managers.NotificationsManager;
import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.ui.changeview.EventsMapView;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsPresenter;
import com.sonda.emsysmobile.ui.extensions.ExtensionsListFragment;
import com.sonda.emsysmobile.ui.fragments.ExternalServiceQueryFragment;
import com.sonda.emsysmobile.ui.fragments.MapExtensionsFragment;
import com.sonda.emsysmobile.ui.fragments.NotificationsFragment;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.dialogs.EventFilterDialogFragment;
import com.sonda.emsysmobile.utils.UIUtils;
import com.squareup.picasso.Picasso;

public class HomeActivity extends RootActivity
        implements OnListFragmentInteractionListener,
        EventFilterDialogFragment.OnEventFilterDialogListener,
        NotificationsFragment.OnFragmentInteractionListener {

    public static final String TAG = HomeActivity.class.getName();
    private static final String NOTIFICATION_RECEIVED = "notification_received";
    private EventsMapView mMapView;
    private FrameLayout mMapContainer;
    private FrameLayout mFragmentsContainer;
    private FloatingActionButton mFloatingButton;
    private boolean mContainerCollapsed;
    private MapExtensionsFragment mMapExtensionsFragment;
    private NotificationsFragment mNotificationsFragment;
    private BroadcastReceiver broadcastReceiverNotifications = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setNotificationActive(true);
        }
    };

    @Override
    public final void onEventFilter(String selectedFilter) {
        UIUtils.hideSoftKeyboard(this);
        if (mMapContainer.getVisibility() == View.VISIBLE) {
            mMapExtensionsFragment.setFilter(selectedFilter);
            mMapExtensionsFragment.getMapEvents();
        } else {
            ExtensionsListFragment extensionsListFragment =
                    (ExtensionsListFragment) getSupportFragmentManager()
                            .findFragmentByTag(ExtensionsListFragment.class.getSimpleName());
            if (extensionsListFragment != null) {
                extensionsListFragment.setFilter(selectedFilter);
                extensionsListFragment.loadData(false);
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_home, R.id.activity_main_layout,
                "Listado de eventos", RootActivity.EVENT_LIST);

        // Start KeepAlive service.
        Intent intent = new Intent(HomeActivity.this, KeepAliveService.class);
        startService(intent);

        // Creating notifications manager singleton
        NotificationsManager.getInstance(this);

        // Creating multimedia manager singleton
        MultimediaManager.getInstance(this);

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

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.embedded, menu);
        menu.findItem(R.id.menu_1).setIcon(
                new IconicsDrawable(this, GoogleMaterial.Icon.gmd_filter_list).color(Color.WHITE)
                        .actionBar());
        menu.findItem(R.id.menu_item_notif_on).setIcon(
                new IconicsDrawable(this, GoogleMaterial.Icon.gmd_notifications_active)
                        .color(Color.WHITE).actionBar());
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_1) {
            showMapView(false);

            // Primero se redirige al listado.
            ExtensionsListFragment extensionsListFragment =
                    (ExtensionsListFragment) getSupportFragmentManager()
                            .findFragmentByTag(ExtensionsListFragment.class.getSimpleName());
            if (extensionsListFragment == null) {
                extensionsListFragment = new ExtensionsListFragment();
            }
            replaceFragment(extensionsListFragment, ExtensionsListFragment.class.getSimpleName());

            // Luego se abre el diálogo para elegir el filtro.
            FragmentManager fm = getSupportFragmentManager();
            EventFilterDialogFragment eventFilterDialogFragment =
                    EventFilterDialogFragment.newInstance();
            eventFilterDialogFragment
                    .show(fm, eventFilterDialogFragment.getClass().getSimpleName());

        } else if (item.getItemId() == R.id.menu_item_notif_on) {

            showMapView(false);
            setNotificationActive(false);

            FragmentManager fragmentManager = getSupportFragmentManager();
            mNotificationsFragment = NotificationsFragment.newInstance();
            mNotificationsFragment
                    .show(fragmentManager, NotificationsFragment.class.getSimpleName());
        }

        return true;
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

    private void replaceFragment(Fragment fragment, String fragmentTAG) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTAG).commit();
    }

    private void setNotificationActive(boolean active) {
        //TODO: show badge in notifications icon
    }

    public final void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected final void goToEventCreateView() {
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
    protected final void goToEventListView() {
        getSupportActionBar().setTitle("Listado de eventos");
        showFilterMenu(true);
        showMapView(false);
        ExtensionsListFragment extensionsFragment =
                (ExtensionsListFragment) getSupportFragmentManager()
                        .findFragmentByTag(ExtensionsListFragment.class.getSimpleName());
        if (extensionsFragment == null) {
            extensionsFragment = new ExtensionsListFragment();
        }
        replaceFragment(extensionsFragment, ExtensionsListFragment.class.getSimpleName());
    }

    @Override
    protected final void goToEventMapView() {
        getSupportActionBar().setTitle("Eventos en el mapa");
        showFilterMenu(false);
        showMapView(true);
        if (mMapView == null) {
            mMapView = EventsMapView.getInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.map_container, mMapView, EventsMapView.class.getSimpleName())
                    .commit();
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
    protected final void goToExternalServiceView() {
        getSupportActionBar().setTitle("Servicio externo");
        showFilterMenu(false);
        showMapView(false);
        ExternalServiceQueryFragment externalServiceFragment =
                (ExternalServiceQueryFragment) getSupportFragmentManager()
                        .findFragmentByTag(ExternalServiceQueryFragment.class.getSimpleName());
        if (externalServiceFragment == null) {
            externalServiceFragment = new ExternalServiceQueryFragment();
        }
        replaceFragment(externalServiceFragment,
                ExternalServiceQueryFragment.class.getSimpleName());
    }

    private void showFilterMenu(boolean visible) {
        if (visible) {
            findViewById(R.id.menu_1).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.menu_1).setVisibility(View.GONE);
        }
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
    public final void onNotificationSelected(Notification notification) {
        mNotificationsFragment.dismiss();
        EventDetailsPresenter.loadEventDetails(HomeActivity.this, notification.getEventId(),
                notification.getExtensionId());
    }

    @Override
    public final void onPause() {
        super.onPause();

        //We should unregister Broadcast Reciever when te fragment is paused
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastReceiverNotifications);
    }

    @Override
    public final void onResume() {
        super.onResume();

        //We wants than Broadcast Receiver be registered when the fragment is active
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiverNotifications,
                        new IntentFilter(NOTIFICATION_RECEIVED));
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