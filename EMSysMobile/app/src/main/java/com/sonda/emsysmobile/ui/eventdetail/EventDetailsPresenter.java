package com.sonda.emsysmobile.ui.eventdetail;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.ReportTimeResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.ReportTimeRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.UpdateDescriptionRequest;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.Settings.Global.getString;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 15-Oct-16.
 */

public final class EventDetailsPresenter {

    private static final String TAG = EventDetailsPresenter.class.getName();
    private static EventDetailsView mEventDetailsView;
    private static EventDetailMapView mMapFragment;

    private EventDetailsPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }

    /**
     * Carga los detalles del evento (si es necesario), y se encarga de comenzar
     * la inicializacion de la vista del detalle del evento.
     *
     * @param context
     * @param eventId
     * @param eventExtensionId
     */
    public static void loadEventDetails(final Context context, final int eventId, final int
            eventExtensionId) {
        final EventManager eventManager = EventManager.getInstance(context);

        EventDto event = eventManager.getLocalEventDetail(eventId);
        if (event != null) {
            initEventDetailsView(context, event);

            eventManager.getEventDetail(eventId, new ApiCallback<EventDto>() {
                @Override
                public void onSuccess(EventDto event) {
                    List<ExtensionDto> orderedExtensions =
                            orderExtensions(event.getExtensions(), eventExtensionId);
                    eventManager.setEventAsRead(event);
                    event.setExtensions(orderedExtensions);
                    updateMapFragment();
                    mEventDetailsView.updateViewData(event);
                }

                @Override
                public void onLogicError(String errorMessage, int errorCode) {
                    if (((context instanceof Activity) && (!((Activity) context).isFinishing()))) {
                        UIUtils.handleErrorMessage(context, errorCode, errorMessage);
                    }
                }

                @Override
                public void onNetworkError(VolleyError error) {
                }
            });
        }
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static List<ExtensionDto> orderExtensions(List<ExtensionDto> extensions, int
            extensionId) {
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
        mEventDetailsView = (EventDetailsView) context;
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
            mEventDetailsView.showMap();
        }
    }

    public static void updateMapFragment() {
        if (mMapFragment != null) {
            mMapFragment.updateView();
        }
    }

    public static void attachDescriptionForExtension(final Context context, final String
            description, final int extensionId) {
        UpdateDescriptionRequest<EmsysResponse> updateDescriptionRequest =
                new UpdateDescriptionRequest<>(context, EmsysResponse.class);
        updateDescriptionRequest.setAttributes(description, extensionId);
        updateDescriptionRequest.setListener(new Response.Listener<EmsysResponse>() {
            @Override
            public void onResponse(EmsysResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    // Show toast with success message
                    UIUtils.showToast(context, mEventDetailsView.getApplicationContext().getString(R.string.update_desc_success_string));
                } else {
                    if (((context instanceof Activity) && (!((Activity) context).isFinishing()))) {
                        UIUtils.handleErrorMessage(context, response.getCode(), context
                                .getString(R.string.error_generic));
                    }
                }
            }
        });
        updateDescriptionRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Si la request falla por cuestiones de conectividad a Internet, se genera
                // un objeto que almacena la descripcion de la extension, que se intenta enviar
                // en el OfflineService cuando se recupere conectividad.
                if ((error instanceof NetworkError) || (error instanceof ServerError) ||
                        (error instanceof TimeoutError)) {
                    OfflineAttachDescriptionDto offlineAttachDescriptionDto =
                            new OfflineAttachDescriptionDto();
                    // Obtengo informacion del usuario.
                    offlineAttachDescriptionDto.setUserData(GlobalVariables.getUserData());
                    offlineAttachDescriptionDto.setDescription(description);
                    offlineAttachDescriptionDto.setExtensionId(extensionId);
                    offlineAttachDescriptionDto.setTimeStamp(new Date());
                    // Se agrega el dto a la cola de dtos a enviar.
                    try {
                        GlobalVariables.getQueue().put(offlineAttachDescriptionDto);
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getStackTrace().toString());
                    }
                }
                if (((context instanceof Activity) && (!((Activity) context).isFinishing()))) {
                    UIUtils.handleErrorMessage(context, ErrorCodeCategory.NETWORK_ERROR
                            .getNumVal(), context.getString(R.string.error_network_update_desc));
                }
            }
        });
        updateDescriptionRequest.execute();
    }

    public static void showGeolocationAttachView(Intent intent) {
        if (mEventDetailsView != null) {
            mEventDetailsView.startActivityForResult(intent, 0);
        }
    }

    public static void reportTime(final Context context, int extensionId) {
        ReportTimeRequest<ReportTimeResponse> reportTimeRequest =
                new ReportTimeRequest<>(context, ReportTimeResponse.class, extensionId);
        reportTimeRequest.setListener(new Response.Listener<ReportTimeResponse>() {
            @Override
            public void onResponse(ReportTimeResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    // Show toast with success message
                    UIUtils.showToast(context, mEventDetailsView.getApplicationContext().getString(R.string.report_time_success));
                } else if (responseCode == ErrorCodeCategory.TIME_ALREADY_REPORTED.getNumVal()) {
                    UIUtils.showToast(context, mEventDetailsView.getApplicationContext().getString(R.string.time_already_reported));
                } else {
                    if (((context instanceof Activity) && (!((Activity) context).isFinishing()))) {
                        UIUtils.handleErrorMessage(context, response.getCode(), context
                                .getString(R.string.error_generic));
                    }
                }
            }
        });
        reportTimeRequest.execute();
    }
}
