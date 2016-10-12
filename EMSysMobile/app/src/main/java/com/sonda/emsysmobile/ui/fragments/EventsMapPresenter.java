package com.sonda.emsysmobile.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 11-Oct-16.
 */
public class EventsMapPresenter {

    private static final String TAG = EventsMapPresenter.class.getName();

    public static void loadEvents(final Context context, final EventsMapView view) {
        EventManager eventManager = EventManager.getInstance(context);
        eventManager.fetchEvents(new ApiCallback<List<EventDto>>() {
            @Override
            public void onSuccess(List<EventDto> events) {
                List<CustomMarkerData> data = getCustomMarkerData(events);
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

    private static List<CustomMarkerData> getCustomMarkerData(List<EventDto> events) {
        List<CustomMarkerData> data = new ArrayList<>();
        for (EventDto event : events) {
            LatLng coordinates = getUniqueCoordinates(event, data);
            CustomMarkerData dataElement = new CustomMarkerData(
                    "Evento " + Integer.toString(event.getIdentifier()), "", coordinates);
            data.add(dataElement);
        }
        return data;
    }

    /**
     * Obtiene coordenadas unicas para el evento. Esto es necesario en caso de existir
     * eventos con coordenadas en común.
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
     * @param coordinates
     * @param customMarkerDataList
     * @return
     */
    private static boolean duplicateCoordinates(LatLng coordinates, List<CustomMarkerData> customMarkerDataList) {
        for (CustomMarkerData marker : customMarkerDataList) {
            if (marker.getCoordinates().equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public static class CustomMarkerData {

        private String title;
        private String description;
        private LatLng coordinates;

        public CustomMarkerData(String title, String description, LatLng coordinates) {
            this.title = title;
            this.description = description;
            this.coordinates = coordinates;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LatLng getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(LatLng coordinates) {
            this.coordinates = coordinates;
        }
    }


}
