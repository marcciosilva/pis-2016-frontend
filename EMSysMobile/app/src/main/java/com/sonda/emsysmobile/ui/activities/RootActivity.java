package com.sonda.emsysmobile.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.backendcommunication.services.KeepAliveService;
import com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.utils.CrossfadeWrapper;
import com.sonda.emsysmobile.utils.UIUtils;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by mserralta on 30/10/16.
 */

public abstract class RootActivity extends AppCompatActivity {


    private static final String TAG = RootActivity.class.getName();
    public static final int EVENT_LIST = 1;
    public static final int EVENT_MAP_LIST = 2;
    public static final int EXTERNAL_SERVICE = 3;
    public static final int CREATE_EVENT = 4;
    private static final int LOG_OUT = 5;

    protected AccountHeader headerResult = null;
    protected Drawer result = null;
    protected MiniDrawer miniResult = null;
    protected Crossfader crossFader;


    protected void onCreate(Bundle savedInstanceState, int guestActivityId,int guestActivityRootId, String title, int selectedItem) {
        super.onCreate(savedInstanceState);
        setContentView(guestActivityId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.hamburger_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crossFader.crossFade();
            }
        });
        getSupportActionBar().setTitle(title);

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("Despachador de zonas (3)").withIcon("https://avatars3.githubusercontent.com/u/887462?v=3&s=460");
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem().withName("Cerrar sesión").withIdentifier(LOG_OUT)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == LOG_OUT) {
                            //TODO CALL TO LOGOUT FUNCTION
                        }
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.menu_list_events_string).withIcon(GoogleMaterial.Icon.gmd_format_list_bulleted).withIdentifier(EVENT_LIST),
                        new PrimaryDrawerItem().withName(R.string.menu_view_map_string).withIcon(GoogleMaterial.Icon.gmd_map).withIdentifier(EVENT_MAP_LIST),
                        new PrimaryDrawerItem().withName(R.string.menu_external_service_string).withIcon(GoogleMaterial.Icon.gmd_cloud_download).withIdentifier(EXTERNAL_SERVICE),
                        new PrimaryDrawerItem().withName(R.string.menu_create_event_string).withIcon(GoogleMaterial.Icon.gmd_plus).withIdentifier(CREATE_EVENT)


                        ) // add the items we want to use with our Drawer

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()){
                            case CREATE_EVENT:
                                goToEventCreateView();
                                return false;
                            case EVENT_LIST:
                                goToEventListView();
                                return false;
                            case EXTERNAL_SERVICE:
                                goToExternalServiceView();
                                return false;
                            case EVENT_MAP_LIST:
                                goToEventMapView();
                                return false;
                            case LOG_OUT:
                                logout();
                                return false;
                            default:
                                return false;
                        }

                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.menu_logout_string).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(LOG_OUT)
                )
                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                .buildView();

        result.setSelection(selectedItem, false);
        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
        miniResult = result.getMiniDrawer();

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
        crossFader = new Crossfader()
                .withContent(findViewById(guestActivityRootId))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();


        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader != null && crossFader.isCrossFaded()) {
            crossFader.crossFade();
        } else {
            DialogFragment dialog = UIUtils.getSimpleDialog("Debe cerrar sesión para modificar su rol.");
            dialog.show(getSupportFragmentManager(), TAG);
        }
    }

    private void replaceFragment(Fragment fragment, String fragmentTAG) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTAG).commit();
    }


    protected abstract void goToEventCreateView();

    protected abstract void goToEventListView();

    protected abstract void goToEventMapView();

    protected abstract void goToExternalServiceView();

    private void logout() {
        //TODO Move this logic to other service
        LogoutRequest<LoginLogoutResponse> request =
                new LogoutRequest<>(getApplicationContext(), LoginLogoutResponse.class);
        request.setListener(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    // Stop KeepAlive service.
                    Intent intent = new Intent(RootActivity.this, KeepAliveService.class);
                    stopService(intent);
                    // Se reinicia el token de autenticacion.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit()
                            .putString("access_token", "").commit();
                    EventManager.getInstance(RootActivity.this).onLogout();
                    goToSplash();
                } else {
                    String errorMsg = response.getInnerResponse().getMsg();
                    if (!isFinishing()) {
                        handleErrorMessage(RootActivity.this, responseCode, errorMsg);
                    }
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
                if (!isFinishing()) {
                    handleVolleyErrorResponse(RootActivity.this, error, new DialogInterface
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
