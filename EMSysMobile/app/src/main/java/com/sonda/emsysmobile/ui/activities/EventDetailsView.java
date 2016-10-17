package com.sonda.emsysmobile.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsView extends AppCompatActivity {

    public static final String EVENT_ID = "event.identifier";
    public static final String EVENT_EXTENSION_ZONE = "extension.zone";
    private EventDto mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Bundle bundle = getIntent().getExtras();

        //TODO contemplar el hecho de que la key "extension.zone" puede no traer nada
        //si es llamada desde el mapa de eventos
        if (bundle.containsKey(EVENT_ID)) {
            // Cargar modelo según el identificador.
            EventManager eventManager = EventManager.getInstance(EventDetailsView.this);
            mEvent = eventManager.getEvent(bundle.getInt(EVENT_ID));
        }

        if (mEvent != null) {
            ((TextView) findViewById(R.id.informant_name_value)).setText(mEvent.getInformant());
            ((TextView) findViewById(R.id.informant_phone_value)).setText(mEvent.getPhone());
            ((TextView) findViewById(R.id.informant_street_value)).setText(mEvent.getStreet());
            ((TextView) findViewById(R.id.informant_corner_value)).setText(mEvent.getCorner());

            ((TextView) findViewById(R.id.category_value)).setText(mEvent.getCategory().getCode());
            ((TextView) findViewById(R.id.type_value)).setText(mEvent.getOrigin());
        }
    }

}
