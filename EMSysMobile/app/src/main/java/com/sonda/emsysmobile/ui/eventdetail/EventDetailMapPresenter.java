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
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.ui.changeview.CustomMarkerData;
import com.sonda.emsysmobile.utils.UIUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 11-Oct-16.
 */
public class EventDetailMapPresenter {

    private static final String TAG = EventDetailMapPresenter.class.getName();
    private static EventDto mEventDto = null;

    private EventDetailMapPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static void loadEventDetails(final Context context, final EventDetailMapView view) {
        if (mEventDto != null) {
            EventManager eventManager = EventManager.getInstance(context);
            eventManager.getEventDetail(mEventDto.getIdentifier(),
                    new ApiCallback<EventDto>() {
                        @Override
                        public void onSuccess(EventDto event) {
                            List<List<CustomMarkerData>> data = getCustomMarkerData(event, context);
                            view.updateEventData(data);
                        }

                        @Override
                        public void onLogicError(String errorMessage, int errorCode) {
                            UIUtils.handleErrorMessage(context, errorCode, errorMessage);
                        }

                        @Override
                        public void onNetworkError(VolleyError error) {
                            handleVolleyErrorResponse(context, error, new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loadEventDetails(context, view);
                                }
                            });
                        }
                    });
        }


    }

    private static List<List<CustomMarkerData>> getCustomMarkerData(EventDto event,
                                                                    final Context context) {
        List<List<CustomMarkerData>> data = new ArrayList<>();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        if ((event.getLatitude() != 0.0) || (event.getLongitude() != 0.0)) {
            List<CustomMarkerData> eventMarkers = new ArrayList<>();
            LatLng coordinates = getUniqueCoordinates(new LatLng(event.getLatitude(),
                    event.getLongitude()), data);
            CustomMarkerData dataElement = new CustomMarkerData(
                    "Evento " + Integer.toString(event.getIdentifier()) + " - " +
                            df.format(event.getCreatedDate()),
                    context.getString(R.string.map_event_view_detail).toUpperCase(),
                    coordinates);
            eventMarkers.add(dataElement);
            data.add(eventMarkers);
        }
        List<ExtensionDto> extensions = event.getExtensions();
        for (ExtensionDto extension : extensions) {
            List<GeolocationDto> geolocations = extension.getGeolocations();
            if ((geolocations != null) && (geolocations.size() > 0)) {
                List<CustomMarkerData> tmpList = new ArrayList<>();
                for (GeolocationDto geolocation : geolocations) {
                    LatLng coordinates = getUniqueCoordinates(new LatLng(geolocation.getLatitude(),
                            geolocation.getLongitude()), data);
                    CustomMarkerData dataElement = new CustomMarkerData(
                            "Extensión " + Integer.toString(extension.getIdentifier()) + " - " +
                                    df.format(event.getCreatedDate()),
                            null,
                            coordinates);
                    tmpList.add(dataElement);
                }
                data.add(tmpList);
            }
        }
        return data;
    }

    /**
     * Obtiene coordenadas unicas para el evento/extension. Esto es necesario en caso de existir
     * eventos/extensiones con coordenadas en común.
     *
     * @param originalCoordinates
     * @param data
     * @return
     */
    private static LatLng getUniqueCoordinates(LatLng originalCoordinates,
                                               List<List<CustomMarkerData>> data) {
        LatLng ll = new LatLng(originalCoordinates.latitude, originalCoordinates.longitude);
        while (duplicateCoordinates(originalCoordinates, data)) {
            Log.d(TAG, "Colision entre coordenadas de eventos.");
            // Offset para longitud.
            double dx = Math.random();
            // Offset para latitud.
            double dy = Math.random();
            final int i = 180;
            final int i1 = 6378137;
            double latitude = originalCoordinates.latitude + (i / Math.PI) * (dy / i1);
            double longitude = originalCoordinates.longitude + (i / Math.PI) * (dx / i1)
                    / Math.cos(Math.PI / i * originalCoordinates.latitude);
            ll = new LatLng(latitude, longitude);
        }
        // Se informa si hubo un cambio de coordenadas debido a colisiones.
        if ((ll.latitude != originalCoordinates.latitude) ||
                (ll.longitude != originalCoordinates.longitude)) {
            Log.d(TAG, "Colision resuelta, se pasa de " + (new LatLng(originalCoordinates.latitude,
                    originalCoordinates.longitude).toString() + " a " + ll.toString()));
        }
        return ll;
    }


    /**
     * Chequea si las coordenadas dadas por coordinates ya se encuentran en customMarkerDataList.
     *
     * @param coordinates
     * @param listOfMarkerLists
     * @return
     */
    private static boolean duplicateCoordinates(LatLng coordinates,
                                                List<List<CustomMarkerData>> listOfMarkerLists) {
        for (List<CustomMarkerData> list : listOfMarkerLists) {
            for (CustomMarkerData marker : list) {
                if (marker.getCoordinates().equals(coordinates)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setEventDto(EventDto eventDto) {
        mEventDto = eventDto;
    }
}
