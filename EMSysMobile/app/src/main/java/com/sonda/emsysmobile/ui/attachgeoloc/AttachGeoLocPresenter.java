package com.sonda.emsysmobile.ui.attachgeoloc;

import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;

/**
 * Created by marccio on 10/25/16.
 */

public class AttachGeoLocPresenter {

    private static GeolocationDto mGeolocationDto = null;
    private static final String TAG = AttachGeoLocPresenter.class.getName();

    private AttachGeoLocPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }


    public static boolean sendGeoLocation() {
        return false;
    }
}
