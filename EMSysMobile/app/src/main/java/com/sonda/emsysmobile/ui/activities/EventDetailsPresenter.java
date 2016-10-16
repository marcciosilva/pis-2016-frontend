package com.sonda.emsysmobile.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by marccio on 15-Oct-16.
 */

public class EventDetailsPresenter {

    /**
     * Carga los detalles del evento (si es necesario), y se encarga de comenzar
     * la inicializacion de la vista del detalle del evento.
     * @param context
     * @param extensionId
     * @param extensionZoneName
     */
    public static void loadEventDetails(Context context, int extensionId, String extensionZoneName) {
        //TODO hacer request para obtener datos detallados del evento
        Bundle args = new Bundle();
        args.putInt(EventDetailsView.EVENT_ID, extensionId);
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
        intent.putExtras(args);
        context.startActivity(intent);
    }

}
