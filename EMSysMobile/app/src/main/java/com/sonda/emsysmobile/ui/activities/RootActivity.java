package com.sonda.emsysmobile.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.backendcommunication.services.KeepAliveService;
import com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.UserDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.utils.CrossfadeWrapper;
import com.sonda.emsysmobile.utils.UIUtils;


import java.util.List;

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

    private UserDto userData;
    protected AccountHeader headerResult = null;
    protected Drawer result = null;
    protected MiniDrawer miniResult = null;
    protected Crossfader crossFader;


    protected void onCreate(Bundle savedInstanceState, int guestActivityId, int
            guestActivityRootId, String title, int selectedItem) {
        super.onCreate(savedInstanceState);
        setContentView(guestActivityId);

        userData = GlobalVariables.getUserData();

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

        String userName = getUsername();
        String roleLabel = getRoleLabel();
        final IProfile profile = new ProfileDrawerItem().withName(userName).withEmail(roleLabel)
                .withIcon("https://avatars3.githubusercontent.com/u/887462?v=3&s=460");
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (profile instanceof IDrawerItem &&
                                ((IDrawerItem) profile).getIdentifier() == LOG_OUT) {
                            logout();
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        addZoneName(headerResult);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.menu_list_events_string)
                                .withIcon(GoogleMaterial.Icon.gmd_format_list_bulleted)
                                .withIdentifier(EVENT_LIST),
                        new PrimaryDrawerItem().withName(R.string.menu_view_map_string)
                                .withIcon(GoogleMaterial.Icon.gmd_map)
                                .withIdentifier(EVENT_MAP_LIST),
                        new PrimaryDrawerItem().withName(R.string.menu_external_service_string)
                                .withIcon(GoogleMaterial.Icon.gmd_cloud_download)
                                .withIdentifier(EXTERNAL_SERVICE),
                        new PrimaryDrawerItem().withName(R.string.menu_create_event_string)
                                .withIcon(GoogleMaterial.Icon.gmd_plus).withIdentifier(CREATE_EVENT)


                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()) {
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
                        new PrimaryDrawerItem().withName(R.string.menu_logout_string)
                                .withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(LOG_OUT)
                )
                .buildView();

        result.setSelection(selectedItem, false);
        miniResult = result.getMiniDrawer();

        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        crossFader = new Crossfader()
                .withContent(findViewById(guestActivityRootId))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withGmailStyleSwiping()
                .withSavedInstance(savedInstanceState)
                .build();

        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        crossFader.getCrossFadeSlidingPaneLayout()
                .setShadowResourceLeft(R.drawable.material_drawer_shadow_left);
    }

    private String getUsername() {
        return userData.getUsername();
    }

    private String getRoleLabel() {
        if (userData.isZoneDispatcher()) {
            return "Despachado de zona (" + String.valueOf(userData.getRoles().getZones().size()) +
                    ")";
        } else if (userData.isResource()) {
            List<ResourceDto> resources = userData.getRoles().getResources();
            return "Recurso (" + resources.get(0).getCode() + ")";
        }
        return "Usuario sin rol";
    }


    private void addZoneName(AccountHeader headerAccount) {
        if (userData.isZoneDispatcher()) {
            List<ZoneDto> zones = userData.getRoles().getZones();
            for (ZoneDto zone : zones) {
                headerAccount.addProfiles(new ProfileSettingDrawerItem().withName(zone.getName()));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (crossFader != null && crossFader.isCrossFaded()) {
            crossFader.crossFade();
        } else {
            DialogFragment dialog =
                    UIUtils.getSimpleDialog("Debe cerrar sesión para modificar su rol.");
            dialog.show(getSupportFragmentManager(), TAG);
        }
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

}
