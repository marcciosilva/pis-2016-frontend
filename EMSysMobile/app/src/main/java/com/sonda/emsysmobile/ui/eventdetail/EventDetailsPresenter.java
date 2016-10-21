package com.sonda.emsysmobile.ui.eventdetail;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.ui.views.CustomScrollView;
import com.sonda.emsysmobile.utils.UIUtils;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

import java.io.Serializable;
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
     *
     * @param context
     * @param eventId
     * @param eventExtensionId
     */
    public static void loadEventDetails(final Context context, final String eventId, final String eventExtensionId) {
        EventManager eventManager = EventManager.getInstance(context);
        eventManager.getEventDetail(eventId, new ApiCallback<EventDto>() {
            @Override
            public void onSuccess(EventDto event) {
                if (eventExtensionId != null) {
                    //Fixme esta llegando un evento con id != eventId
                    List<ExtensionDto> orderedExtensions = orderExtensions(event.getExtensions(), Integer.parseInt(eventExtensionId));
                    event.setExtensions(orderedExtensions);
                }
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
                        loadEventDetails(context, eventId, eventExtensionId);
                    }
                });
            }
        });
    }

    /**
     * Inicializa la vista para el detalle del evento, pasandole los datos que correspondan
     * para que muestre.
     *
     * @param context
     * @param event
     */
    private static void initEventDetailsView(Context context, EventDto event) {
        Intent intent = new Intent(context, EventDetailsView.class);
        EventDetailMapPresenter.setEventDto(event);
        intent.putExtra("EventDto", event);
        context.startActivity(intent);
    }

    private static List<ExtensionDto> orderExtensions(List<ExtensionDto> extensions, int extensionId) {
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
        if (currentExtension != null) {
            result.add(0, currentExtension);
        }
        return result;
    }

    public static void initMapFragment(Context context, EventDto event) {
        boolean hasGeolocation = false;
        Log.d(TAG, "Event ID = " + Integer.toString(event.getIdentifier()));
        Log.d(TAG, "LATITUD: " + Double.toString(event.getLatitude()));
        Log.d(TAG, "LONGITUD: " + Double.toString(event.getLongitude()));
        if ((event.getLatitude() != 0) || (event.getLongitude() != 0)) {
            hasGeolocation = true;
        } else {
            // No hay coordenadas del evento.
            for (ExtensionDto extensionDto : event.getExtensions()) {
                List<GeolocationDto> geolocations = extensionDto.getGeolocations();
                if (geolocations.size() > 0) {
                    hasGeolocation = true;
                }
                //TODO implementar logica que soporte Dto de geoubicacion en extensiones
            }
        }
        if (hasGeolocation) {
            Log.d(TAG, "Assigning event id " + Integer.toString(event.getIdentifier())
                    + " to EventDetailMapPresenter");
            EventDetailMapView mapFragment = EventDetailMapView.getInstance();
            CustomScrollView mainScrollView = (CustomScrollView) ((Activity)context).getWindow()
                    .getDecorView().findViewById(R.id.main_scrollview_map_detail);
            mapFragment.initializeView((FragmentActivity)context, mainScrollView);
            mapFragment.showView();
        }
    }


}
