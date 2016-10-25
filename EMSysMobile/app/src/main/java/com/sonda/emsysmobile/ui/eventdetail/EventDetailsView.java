package com.sonda.emsysmobile.ui.eventdetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.activities.attachgeoloc.AttachGeoLocActivity;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.utils.DateUtils;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsView extends AppCompatActivity implements
        OnListFragmentInteractionListener {

    public static final String EVENT_ID = "event.identifier";
    public static final String EVENT_EXTENSION_ID = "extension.zone";
    public static final String EVENT_HAS_GEOLOCATION = "hasGeolocation";
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

    private Button mGeoLocButton;

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

        final Button button = (Button) findViewById(R.id.button_geoloc);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToGeoLocActivity();
            }
        });

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

    public void goToGeoLocActivity(){
        Intent intent = new Intent(this, AttachGeoLocActivity.class);
        Bundle b = new Bundle();
        // OJO! Aca hay que pasar los datos de la extension a la que se le quiere adjuntar la GeoLoc
        // Esta asi para probar
        b.putInt("ExtId", mEvent.getIdentifier());
        b.putDouble("ExtLat", mEvent.getLatitude());
        b.putDouble("ExtLong", mEvent.getLongitude());
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto event) {

    }
}
