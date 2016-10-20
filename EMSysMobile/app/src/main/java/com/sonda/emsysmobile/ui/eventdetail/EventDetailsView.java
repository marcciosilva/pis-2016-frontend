package com.sonda.emsysmobile.ui.eventdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.ui.views.CustomScrollView;
import com.sonda.emsysmobile.utils.DateUtils;
import com.sonda.emsysmobile.utils.UIUtils;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsView extends AppCompatActivity {

    public static final String EVENT_ID = "event.identifier";
    public static final String EVENT_EXTENSION_ID = "extension.zone";
    public static final String EVENT_HAS_GEOLOCATION = "hasGeolocation";
    private EventDto mEvent;
    private EventDetailMapView mMapFragment = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        //si es llamada desde el mapa de eventos
//        if (bundle.containsKey(EVENT_ID)) {
//            // Cargar modelo según el identificador.
//            EventManager eventManager = EventManager.getInstance(EventDetailsView.this);
//            mEvent = eventManager.getEvent(bundle.getInt(EVENT_ID));
//        }

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

        // Inicializacion de fragment de mapa.
//        if (bundle.getBoolean(EVENT_HAS_GEOLOCATION)) {
        mMapFragment = EventDetailMapView.getInstance();
        CustomScrollView mainScrollView = (CustomScrollView) findViewById(R.id.main_scrollview_map_detail);
        mMapFragment.initializeView(this, mainScrollView);
        mMapFragment.showView();
//        }

    }

    public void updateViewData(EventDto event) {
        mEvent = event;

        if (mEvent != null) {
            mInformantName.setText(mEvent.getInformant());
            mInformantPhone.setText(mEvent.getPhone());

            mCreatedDate.setText(DateUtils.dateToString(mEvent.getCreatedDate()));
            mStatus.setText(mEvent.getStatus());

            mStreet.setText(mEvent.getStreet());
            mNumber.setText(mEvent.getNumber());
            mCorner.setText(mEvent.getCorner());

            if (mEvent.getCategory() != null) {
                mCategory.setText(mEvent.getCategory().getKey());
            }
            mSector.setText(mEvent.getSectorCode());

            mType.setText("Aplicación");
            mOrigin.setText(mEvent.getOrigin());
        }

    }

}
