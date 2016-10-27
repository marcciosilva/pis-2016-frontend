package com.sonda.emsysmobile.ui.changeview;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsPresenter;
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
public final class EventsMapPresenter {

    private static final String TAG = EventsMapPresenter.class.getName();

    private EventsMapPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static void loadEvents(final Context context, final EventsMapView view) {
        EventManager eventManager = EventManager.getInstance(context);
        eventManager.fetchEvents(new ApiCallback<List<EventDto>>() {
            @Override
            public void onSuccess(List<EventDto> events) {
                List<CustomMarkerData> data = getCustomMarkerData(events, context);
                view.updateEventsData(data);
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
                        loadEvents(context, view);
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
            final int i = 180;
            final int i1 = 6378137;
            double latitude = event.getLatitude() + (i / Math.PI) * (dy / i1);
            double longitude = event.getLongitude() + (i / Math.PI) * (dx / i1)
                    / Math.cos(Math.PI / i * event.getLatitude());
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

    /**
     * Se encarga de hacer lo necesario para que el presenter del detalle del evento
     * pueda encargarse de mostrar la vista correspondiente.
     * Si el evento no se encuentra, se devuelve false, y si la operacion es exitosa
     * se devuelve true.
     *
     * @param context
     * @param customMarkerData
     * @return
     */
    public static boolean showEventDetail(final Context context, CustomMarkerData
            customMarkerData) {
        int eventId = -1;
        // Obtengo id del evento a partir del titulo del marker.
        Pattern p = Pattern.compile(".* (\\d)+ -");
        Matcher m = p.matcher(customMarkerData.getTitle());
        if (m.find()) {
            eventId = Integer.parseInt(m.group(1));
        }
        if (eventId != -1) {
            try {
                final int eventExtensionId = -1;
                EventDetailsPresenter
                        .loadEventDetails(context, eventId, eventExtensionId);
            } catch (NullPointerException e) {
                UIUtils.handleErrorMessage(context, ErrorCodeCategory.LOGIC_ERROR.getNumVal(),
                        context.getString(R.string.error_internal));
                Log.d(TAG, e.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }
}
