package com.sonda.emsysmobile.ui.eventdetail;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.utils.UIUtils;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

import java.util.ArrayList;
import java.util.List;

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
     * @param eventExtensionId
     */
    public static void loadEventDetails(final Context context, final String eventId, final String eventExtensionId) {
        EventManager eventManager = EventManager.getInstance(context);
        eventManager.getEvent(eventId, new ApiCallback<EventDto>() {
            @Override
            public void onSuccess(EventDto event) {
                List<ExtensionDto> orderedExtensions = orderExtensions(event.getExtensions(), Integer.parseInt(eventExtensionId));
                event.setExtensions(orderedExtensions);
                initEventDetailsView(context, event);
            }

            @Override
            public void onLogicError(String errorMessage, int errorCode) {
                UIUtils.handleErrorMessage(context, errorCode, errorMessage);
            }

            @Override
            public void onNetworkError(VolleyError error) {
                handleVolleyErrorResponse(context, error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadEventDetails(context, eventId,eventExtensionId);
                    }
                });
            }
        });

//        Bundle args = new Bundle();
//        args.putString(EventDetailsView.EVENT_ID, eventId);
//        args.putString(EventDetailsView.EVENT_EXTENSION_ID, extensionZoneName);
//        initEventDetailsView(context, args);
    }

    /**
     * Inicializa la vista para el detalle del evento, pasandole los datos que correspondan
     * para que muestre.
     * @param context
     * @param event
     */
    private static void initEventDetailsView(Context context, EventDto event) {
        Intent intent = new Intent(context, EventDetailsView.class);
        EventDetailMapPresenter.setEventDto(event);
        boolean hasGeolocation = true;
        if ((event.getLatitude() == 0) && (event.getLongitude() == 0)) {
            // No hay coordenadas del evento.
//            hasGeolocation = false;
            for (ExtensionDto extensionDto : event.getExtensions()) {
                //TODO implementar logica que soporte Dto de geoubicacion en extensiones
            }
            intent.putExtra(EVENT_HAS_GEOLOCATION, hasGeolocation);
        } else {
            intent.putExtra(EVENT_HAS_GEOLOCATION, hasGeolocation);
        }
        Log.d(TAG, "Has geolocation is " + Boolean.toString(hasGeolocation));
        context.startActivity(intent);
    }

    private static List<ExtensionDto> orderExtensions(List<ExtensionDto> extensions, int extensionId){
        Log.d(TAG, "Extension id: " + Integer.toString(extensionId));
        List<ExtensionDto> result = new ArrayList<>();
        ExtensionDto currentExtension = null;
        if (extensions != null) {
            for (ExtensionDto extension : extensions) {
                if (extension.getIdentifier() == extensionId) {
                    currentExtension = extension;
                } else {
                    result.add(extension);
                }
            }
        }
        if(currentExtension != null) {
            result.add(0, currentExtension);
        }

        return result;

    }


}
