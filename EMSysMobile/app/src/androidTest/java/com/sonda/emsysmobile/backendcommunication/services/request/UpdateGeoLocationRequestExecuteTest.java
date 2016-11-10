package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.UpdateGeoLocationResponse;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class UpdateGeoLocationRequestExecuteTest {

    private UpdateGeoLocationRequest<UpdateGeoLocationResponse> mUpdateGeoLocRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void UpdateGeoLocationRequestTest() throws Exception {
        GeolocationDto geolocationDto = new GeolocationDto(1, "test_user", new Date(), 1.2, 1.2);
        mUpdateGeoLocRequest = new UpdateGeoLocationRequest<>(mActivityRule.getActivity().getApplicationContext(),
                UpdateGeoLocationResponse.class, geolocationDto);
        mUpdateGeoLocRequest.setListener(new Response.Listener<UpdateGeoLocationResponse>(){
            @Override
            public void onResponse(UpdateGeoLocationResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mUpdateGeoLocRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mUpdateGeoLocRequest.execute();
    }
}
