package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;

import static com.sonda.emsysmobile.ui.eventdetail.EventDetailsView.EVENT_HAS_GEOLOCATION;

/**
 * Created by marccio on 15-Oct-16.
 */

public class EventDetailsPresenter {

    private static final String TAG = EventDetailsPresenter.class.getName();

    /**
     * Carga los detalles del evento (si es necesario), y se encarga de comenzar
     * la inicializacion de la vista del detalle del evento.
     * @param context
     * @param eventId
     * @param extensionZoneName
     */
    public static void loadEventDetails(Context context, int eventId, String extensionZoneName) {
        //TODO hacer request para obtener datos detallados del evento
        Bundle args = new Bundle();
        args.putInt(EventDetailsView.EVENT_ID, eventId);
        args.putString(EventDetailsView.EVENT_EXTENSION_ZONE, extensionZoneName);
        initEventDetailsView(context, args);
    }

    /**
     * Inicializa la vista para el detalle del evento, pasandole los datos que correspondan
     * para que muestre.
     * @param context
     * @param args
     */
    private static void initEventDetailsView(Context context, Bundle args) {
        Intent intent = new Intent(context, EventDetailsView.class);
        EventDto eventDto = EventManager.getInstance(context)
                .getEvent(args.getInt(EventDetailsView.EVENT_ID));
        EventDetailMapPresenter.setEventDto(eventDto);
        boolean hasGeolocation = true;
        if ((eventDto.getLatitude() == 0) && (eventDto.getLongitude() == 0)) {
            // No hay coordenadas del evento.
//            hasGeolocation = false;
            for (ExtensionDto extensionDto : eventDto.getExtensions()) {
                //TODO implementar logica que soporte Dto de geoubicacion en extensiones
            }
            args.putBoolean(EVENT_HAS_GEOLOCATION, hasGeolocation);
        } else {
            args.putBoolean(EVENT_HAS_GEOLOCATION, hasGeolocation);
        }
        Log.d(TAG, "Has geolocation is " + Boolean.toString(hasGeolocation));
        intent.putExtras(args);
        context.startActivity(intent);
    }

}
