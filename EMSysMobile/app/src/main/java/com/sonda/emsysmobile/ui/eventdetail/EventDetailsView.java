package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.dialogs.AttachDescriptionDialogFragment;
import com.sonda.emsysmobile.utils.DateUtils;
import com.sonda.emsysmobile.utils.UIUtils;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsView extends AppCompatActivity implements
        OnListFragmentInteractionListener,
        AttachDescriptionDialogFragment.OnAttachDescriptionDialogListener {

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

    private FloatingActionButton mUpdateDescriptionBtn;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

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

    @Override
    protected void onResume() {
        super.onResume();
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
        AttachDescriptionDialogFragment attachDescriptionDialogFragment = AttachDescriptionDialogFragment.newInstance();
        attachDescriptionDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == SHOULD_UPDATE_MAP) {
                Log.d(TAG, "Updating map...");
                EventDetailsPresenter.updateMapFragment();
            }
        }
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto event) {

    }

    @Override
    public void onAttachDescription(String descriptionText) {
        UIUtils.hideSoftKeyboard(this);
        if (mEvent.getExtensions() != null && mEvent.getExtensions().size() > 0) {
            int extensionID = mEvent.getExtensions().get(0).getIdentifier();
            EventDetailsPresenter.attachDescriptionForExtension(this, descriptionText, extensionID);
        }
    }
}
