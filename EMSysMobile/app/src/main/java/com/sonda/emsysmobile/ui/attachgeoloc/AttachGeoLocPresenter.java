package com.sonda.emsysmobile.ui.attachgeoloc;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.UpdateGeoLocationResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.UpdateGeoLocationRequest;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsView;
import com.sonda.emsysmobile.utils.UIUtils;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 10/25/16.
 */

public final class AttachGeoLocPresenter {

    private static GeolocationDto mGeolocationDto = null;
    private static final String TAG = AttachGeoLocPresenter.class.getName();

    private AttachGeoLocPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static boolean sendGeoLocation(final AttachGeoLocView view) {
        if (mGeolocationDto != null) {
            UpdateGeoLocationRequest<UpdateGeoLocationResponse> request =
                    new UpdateGeoLocationRequest<>(
                            view, UpdateGeoLocationResponse.class, mGeolocationDto);
            request.setListener(new Response.Listener<UpdateGeoLocationResponse>() {
                @Override
                public void onResponse(UpdateGeoLocationResponse response) {
                    int responseCode = response.getCode();
                    if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                        // Show toast with success message
                        UIUtils.showToast(view.getApplicationContext(), view.getApplicationContext().getString(R.string.attach_geolocation_success_message));
                        view.setResult(EventDetailsView.SHOULD_UPDATE_MAP);
                        view.finish();
                    } else {
                        if (!view.isFinishing()) {
                            UIUtils.handleErrorMessage(view, response.getCode(), response
                                    .getInnerResponse().getMsg());
                        }
                    }
                }
            });
            request.setErrorListener(new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (!view.isFinishing()) {
                        handleVolleyErrorResponse(view, error, new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendGeoLocation(view);
                            }
                        });
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
