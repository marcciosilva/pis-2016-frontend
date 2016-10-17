package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.ui.changeview.CustomMarkerData;
import com.sonda.emsysmobile.utils.UIUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 11-Oct-16.
 */
public class EventDetailMapPresenter {

    private static final String TAG = EventDetailMapPresenter.class.getName();
    private static EventDto mEventDto = null;

    public static void loadEvent(final Context context, final EventDetailMapView view) {
        //TODO Cambiar esto para solo usar un unico objeto de marker en vez de una lista
        EventManager eventManager = EventManager.getInstance(context);
        eventManager.fetchEvents(new ApiCallback<List<EventDto>>() {
            @Override
            public void onSuccess(List<EventDto> events) {
                List<CustomMarkerData> data = getCustomMarkerData(events, context);
                view.updateEventData(data);
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
                        loadEvent(context, view);
                    }
                });
            }
        });
    }

    private static List<CustomMarkerData> getCustomMarkerData(List<EventDto> events,
                                                              final Context context) {
        List<CustomMarkerData> data = new ArrayList<>();
        for (EventDto event : events) {
            // Se chequea si el evento tiene coordenadas.
            if ((event.getLatitude() != 0.0) && (event.getLongitude() != 0.0)) {
                LatLng coordinates = getUniqueCoordinates(event, data);
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
                CustomMarkerData dataElement = new CustomMarkerData(
                        "Evento " + Integer.toString(event.getIdentifier()) + " - " +
                                df.format(event.getCreatedDate()),
                        context.getString(R.string.map_event_view_detail).toUpperCase(),
                        coordinates);
                data.add(dataElement);
            }
        }
        return data;
    }

    /**
     * Obtiene coordenadas unicas para el evento. Esto es necesario en caso de existir
     * eventos con coordenadas en común.
     *
     * @param event
     * @return
     */
    private static LatLng getUniqueCoordinates(EventDto event, List<CustomMarkerData> data) {
        LatLng ll = new LatLng(event.getLatitude(),
                event.getLongitude());
        while (duplicateCoordinates(ll, data)) {
            Log.d(TAG, "Colision entre coordenadas de eventos.");
            // Offset para longitud.
            double dx = Math.random();
            // Offset para latitud.
            double dy = Math.random();
            double latitude = event.getLatitude() + (180 / Math.PI) * (dy / 6378137);
            double longitude = event.getLongitude() + (180 / Math.PI) * (dx / 6378137)
                    / Math.cos(Math.PI / 180.0 * event.getLatitude());
            ll = new LatLng(latitude, longitude);
        }
        // Se informa si hubo un cambio de coordenadas debido a colisiones.
        if ((ll.latitude != event.getLatitude()) || (ll.longitude != event.getLongitude())) {
            Log.d(TAG, "Colision resuelta, se pasa de " + (new LatLng(event.getLatitude(),
                    event.getLongitude()).toString() + " a " + ll.toString()));
        }
        return ll;
    }


    /**
     * Chequea si las coordenadas dadas por coordinates ya se encuentran en customMarkerDataList.
     *
     * @param coordinates
     * @param customMarkerDataList
     * @return
     */
    private static boolean duplicateCoordinates(LatLng coordinates,
                                                List<CustomMarkerData> customMarkerDataList) {
        for (CustomMarkerData marker : customMarkerDataList) {
            if (marker.getCoordinates().equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public static void setEventDto(EventDto eventDto) {
        mEventDto = eventDto;
    }
}
