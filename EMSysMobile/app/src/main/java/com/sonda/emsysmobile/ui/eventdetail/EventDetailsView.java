package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.attachgeoloc.AttachGeoLocView;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.dialogs.AttachDescriptionDialogFragment;
import com.sonda.emsysmobile.utils.DateUtils;
import com.sonda.emsysmobile.utils.UIUtils;

import com.mikepenz.materialdrawer.app.utils.CrossfadeWrapper;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsView extends AppCompatActivity implements
        OnListFragmentInteractionListener,
        AttachDescriptionDialogFragment.OnAttachDescriptionDialogListener {

    private static final int LOG_OUT = 1;
    public static final int SHOULD_UPDATE_MAP = 1;
    private EventDto mEvent;
    private static final String TAG = EventDetailsView.class.getName();

    private TextView mInformantName;
    private TextView mInformantPhone;
    private TextView mCreatedDate;
    private TextView mStatus;
    private TextView mStreet;
    private TextView mNumber;
    private TextView mCorner;
    private TextView mCategory;
    private TextView mSector;
    private TextView mOrigin;
    private TextView mType;


    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private Crossfader crossFader;

    private FloatingActionButton mUpdateDescriptionBtn;
    private FloatingActionButton mAttachGeolocationBtn;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalle de evento");

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com");
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
                        new PrimaryDrawerItem().withName(R.string.menu_list_events_string).withIcon(R.drawable.ic_list_white_24dp).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.menu_view_map_string).withIcon(R.drawable.ic_map_white_24dp).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false)//,
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            //TODO: Implementar cambiar de pantalla
                        }
                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                .buildView(); //TODO Cambiar a .buildView()

        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
        miniResult = result.getMiniDrawer();

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
       crossFader = new Crossfader()
               .withContent(findViewById(R.id.main_scrollview_map_detail))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();


//        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
//        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

        mInformantName = (TextView) findViewById(R.id.informant_name);
        mInformantPhone = (TextView) findViewById(R.id.informant_phone);

        mCreatedDate = (TextView) findViewById(R.id.event_date_created);
        mStatus = (TextView) findViewById(R.id.event_status);

        mStreet = (TextView) findViewById(R.id.informant_street);
        mCorner = (TextView) findViewById(R.id.informant_corner);
        mNumber = (TextView) findViewById(R.id.informant_number);

        mCategory = (TextView) findViewById(R.id.category);
        mSector = (TextView) findViewById(R.id.informant_sector);

        mType = (TextView) findViewById(R.id.type);
        mOrigin = (TextView) findViewById(R.id.origin);

        mUpdateDescriptionBtn = (FloatingActionButton) findViewById(R.id.button_update_description);
        mUpdateDescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDescriptionDialog();
            }
        });

        mAttachGeolocationBtn = (FloatingActionButton) findViewById(R.id.button_attach_geolocation);
        mAttachGeolocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAttachGeolocationView();
            }
        });

        updateViewData((EventDto) getIntent().getSerializableExtra("EventDto"));

        // Inicializacion de fragment de extensiones.
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            EventDetailExtensionsFragment extensionsFragment =
                    EventDetailExtensionsFragment.newInstance(mEvent);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, extensionsFragment).commit();
        }

        // Inicializacion de fragment de mapa.
        EventDetailsPresenter.initMapFragment(EventDetailsView.this, mEvent);
    }

    public final void updateViewData(EventDto event) {
        mEvent = event;
        if (mEvent != null) {
            if ((mEvent.getInformant() != null) && (!mEvent.getInformant().equals(""))) {
                mInformantName.setText(mEvent.getInformant());
            }

            if ((mEvent.getPhone() != null) && (!mEvent.getPhone().equals(""))) {
                mInformantPhone.setText(mEvent.getPhone());
            }

            if ((mEvent.getCreatedDate() != null) && (mEvent.getCreatedDate() != null)) {
                mCreatedDate.setText(DateUtils.dateToString(mEvent.getCreatedDate()));
            }

            if ((mEvent.getStatus() != null) && (!mEvent.getStatus().equals(""))) {
                mStatus.setText(mEvent.getStatus());
            }

            if ((mEvent.getStreet() != null) && (!mEvent.getStreet().equals(""))) {
                mStreet.setText(mEvent.getStreet());
            }
            if ((mEvent.getNumber() != null) && (!mEvent.getNumber().equals(""))) {
                mNumber.setText(mEvent.getNumber());
            }
            if (mEvent.getCorner() != null && !mEvent.getCorner().equals("")) {
                mCorner.setText(mEvent.getCorner());
            }

            if ((mEvent.getCategory() != null) && (!mEvent.getCategory().getKey().equals(""))) {
                mCategory.setText(mEvent.getCategory().getKey());
            }

            if ((mEvent.getSectorCode() != null) && (!mEvent.getSectorCode().equals(""))) {
                mSector.setText(mEvent.getSectorCode());
            }

            mType.setText("Aplicación");
            if ((mEvent.getOrigin() != null) && (!mEvent.getOrigin().equals(""))) {
                mOrigin.setText(mEvent.getOrigin());
            }
        }
    }

    private void showUpdateDescriptionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AttachDescriptionDialogFragment attachDescriptionDialogFragment =
                AttachDescriptionDialogFragment.newInstance();
        attachDescriptionDialogFragment.show(fm, "fragment_edit_name");
    }

    private void goToAttachGeolocationView() {
        if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
            int extensionID = mEvent.getExtensions().get(0).getIdentifier();
            Intent intent =
                    new Intent(EventDetailsView.this, AttachGeoLocView.class);
            Bundle extras = new Bundle();
            extras.putInt("ExtensionId", extensionID);
            intent.putExtras(extras);
            EventDetailsPresenter.showGeolocationAttachView(intent);
        }
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 0) && (resultCode == SHOULD_UPDATE_MAP)) {
            Log.d(TAG, "Updating map...");
            EventDetailsPresenter.updateMapFragment();
        }
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto event) {

    }

    @Override
    public final void onAttachDescription(String descriptionText) {
        UIUtils.hideSoftKeyboard(this);
        if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
            int extensionID = mEvent.getExtensions().get(0).getIdentifier();
            EventDetailsPresenter.attachDescriptionForExtension(this, descriptionText, extensionID);
        }
    }

//    @Override
//    public final boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_back) {
//            onBackPressed();
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public final boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.top_menu_only_back, menu);
//        return true;
//    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader != null && crossFader.isCrossfaded()) {
            crossFader.crossfade();
        } else {
            super.onBackPressed();
        }
    }
}
