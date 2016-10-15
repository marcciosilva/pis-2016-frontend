package com.sonda.emsysmobile.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;

/**
 * Created by mserralta on 13/10/16.
 */

public class EventDetailsFragment extends Fragment {
    public static final String EVENT_ID = "event.idetifier";
    public static final String EVENT_EXTENSION_ZONE = "extension.zone";
    private EventDto mEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(EVENT_ID)) {
            // Cargar modelo según el identificador
            EventManager eventManager = EventManager.getInstance(getActivity().getApplicationContext());
            mEvent = eventManager.getEvent("0");
//            event = eventManager.getEvent(Integer.parseInt(getArguments().getString(EVENT_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_details, container, false);

        if (mEvent != null) {
            // Toolbar en master-detail
//            Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_detalle);
//            if (toolbar != null)
//                toolbar.inflateMenu(R.menu.menu_detalle_articulo);

            ((TextView) v.findViewById(R.id.informant_name_value)).setText(mEvent.getInformant());
            ((TextView) v.findViewById(R.id.informant_phone_value)).setText(mEvent.getPhone());
            ((TextView) v.findViewById(R.id.informant_street_value)).setText(mEvent.getStreet());
            ((TextView) v.findViewById(R.id.informant_corner_value)).setText(mEvent.getCorner());

            ((TextView) v.findViewById(R.id.category_value)).setText(mEvent.getCategory().getCode());
            ((TextView) v.findViewById(R.id.type_value)).setText(mEvent.getOrigin());
        }

        return v;
    }
}
