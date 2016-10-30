package com.sonda.emsysmobile.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.backendcommunication.services.KeepAliveService;
import com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;
import com.sonda.emsysmobile.ui.changeview.EventsMapView;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsPresenter;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.MultimediaManager;
import com.sonda.emsysmobile.ui.fragments.ExtensionsFragment;
import com.sonda.emsysmobile.ui.fragments.ExternalServiceQueryFragment;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.CustomScrollView;
import com.sonda.emsysmobile.ui.views.dialogs.AttachDescriptionDialogFragment;
import com.sonda.emsysmobile.ui.views.dialogs.EventFilterDialogFragment;
import com.sonda.emsysmobile.utils.UIUtils;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

public class HomeActivity extends AppCompatActivity
        implements OnListFragmentInteractionListener,
        EventFilterDialogFragment.OnEventFilterDialogListener {

    private static final String TAG = HomeActivity.class.getName();
    private EventsMapView mMapFragment = null;
    private ExtensionsFragment mExtensionsFragment;

    @Override
    public final void onEventFilter(String selectedFilter) {
        UIUtils.hideSoftKeyboard(this);
        mExtensionsFragment.setFilter(selectedFilter);
        mExtensionsFragment.getEvents();
    }
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Start KeepAlive service.
        Intent intent = new Intent(HomeActivity.this, KeepAliveService.class);
        startService(intent);

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
            mExtensionsFragment = new ExtensionsFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mExtensionsFragment).commit();
            // Inicializacion de fragment de mapa.
            mMapFragment = EventsMapView.getInstance();
            CustomScrollView mainScrollView = (CustomScrollView) findViewById(R.id.main_scrollview);
            mMapFragment.initializeView(this, mainScrollView);
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public final void onBackPressed() {
        DialogFragment dialog = UIUtils.getSimpleDialog("Debe cerrar sesión para modificar su rol.");
        dialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public final void onListFragmentInteraction(ExtensionDto extension) {
        try {
            String eventIdString = Integer.toString(extension.getEvent().getIdentifier());
            if (eventIdString == null) {
                throw(new NullPointerException("EVENT_ID resulta nulo."));
            }
            String eventExtensionZone = Integer.toString(extension.getIdentifier());
            if (eventExtensionZone == null) {
                throw(new NullPointerException("EVENT_EXTENSION_ID resulta nulo."));
            }
            EventDetailsPresenter.loadEventDetails(HomeActivity.this, extension.getEvent()
                    .getIdentifier(), extension.getIdentifier());
        } catch (NullPointerException e){
            UIUtils.handleErrorMessage(this, ErrorCodeCategory.LOGIC_ERROR.getNumVal(),
                    getString(R.string.error_internal));
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        String textString = "text";
        if ((item.getItemId() != R.id.menu_view_map_button) && (item.getItemId() != R.id.menu_filter_button)) {
            mMapFragment.hideView();
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
                mExtensionsFragment = (ExtensionsFragment) getSupportFragmentManager()
                                .findFragmentByTag(ExtensionsFragment.class.getSimpleName());
                if (mExtensionsFragment == null) {
                    mExtensionsFragment = new ExtensionsFragment();
                    replaceFragment(mExtensionsFragment, ExtensionsFragment.class.getSimpleName());
                }
                return true;
            case R.id.menu_external_service_button:
                ExternalServiceQueryFragment externalServiceFragment = (ExternalServiceQueryFragment) getSupportFragmentManager().findFragmentByTag(ExternalServiceQueryFragment.class.getSimpleName());
                if (externalServiceFragment == null) {
                    externalServiceFragment = new ExternalServiceQueryFragment();
                    replaceFragment(externalServiceFragment, ExternalServiceQueryFragment.class.getSimpleName());
                }
                return true;
            case R.id.menu_view_map_button:
                mMapFragment.showView();
                mExtensionsFragment = (ExtensionsFragment) getSupportFragmentManager().findFragmentByTag(ExtensionsFragment.class.getSimpleName());
                if (mExtensionsFragment == null) {
                    mExtensionsFragment = new ExtensionsFragment();
                    replaceFragment(mExtensionsFragment, ExtensionsFragment.class.getSimpleName());
                }
                return true;
            case R.id.menu_logout_button:
                logout();
                return true;
            case R.id.menu_filter_button:
                // Primero se redirige al listado.
                mExtensionsFragment = (ExtensionsFragment) getSupportFragmentManager().findFragmentByTag(ExtensionsFragment.class.getSimpleName());
                if (mExtensionsFragment == null) {
                    mExtensionsFragment = new ExtensionsFragment();
                    replaceFragment(mExtensionsFragment, ExtensionsFragment.class.getSimpleName());
                }
                // Luego se abre el diálogo para elegir el filtro.
                FragmentManager fm = getSupportFragmentManager();
                EventFilterDialogFragment eventFilterDialogFragment = EventFilterDialogFragment.newInstance();
                eventFilterDialogFragment.show(fm, "fragment_edit_name");
                return true;
            default:
                // Accion no reconocida, se lo delega a la superclase.
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment, String fragmentTAG) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTAG).commit();
    }

    private void logout() {
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
                    EventManager.getInstance(HomeActivity.this).onLogout();
                    // Se borran los archivos internos de la aplicacion, que pueden no
                    // necesitarse en la proxima sesion que se inicie.
                    MultimediaManager.getInstance(HomeActivity.this).clearInternalStorage();
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
                handleVolleyErrorResponse(HomeActivity.this, error, new DialogInterface
                        .OnClickListener() {
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
        finish();
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