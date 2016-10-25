package com.sonda.emsysmobile.ui.attachgeoloc;

import android.content.Context;
import android.support.v4.app.DialogFragment;

import com.android.volley.Response;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.UpdateGeoLocationResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.UpdateGeoLocationRequest;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.utils.UIUtils;

/**
 * Created by marccio on 10/25/16.
 */

public class AttachGeoLocPresenter {

    private static GeolocationDto mGeolocationDto = null;
    private static final String TAG = AttachGeoLocPresenter.class.getName();

    private AttachGeoLocPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static boolean sendGeoLocation(final Context context) {
        if (mGeolocationDto != null) {
            UpdateGeoLocationRequest<UpdateGeoLocationResponse> request =
                    new UpdateGeoLocationRequest<>(
                            context, UpdateGeoLocationResponse.class, mGeolocationDto);
            request.setListener(new Response.Listener<UpdateGeoLocationResponse>() {
                @Override
                public void onResponse(UpdateGeoLocationResponse response) {
                    int responseCode = response.getCode();
                    if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                        DialogFragment dialog = UIUtils.getSimpleDialog(
                                context.getString(R.string.attach_geolocation_success_message));
                        dialog.show(((AttachGeoLocView) context).getSupportFragmentManager(), TAG);
                    } else {
                        UIUtils.handleErrorMessage(context, response.getCode(), response
                                .getInnerResponse().getMsg());
                    }
                }
            });
            request.execute();
            return true;
        } else {
            return false;
        }
    }

    public static void setGeoLocation(int extensionId, double latitude,
                                      double longitude) {
        mGeolocationDto = new GeolocationDto(extensionId, null, null, latitude, longitude);
    }


}
