package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.UpdateGeoLocationResponse;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Pape on 8/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class UpdateDescriptionRequestExecuteTest {

    private UpdateDescriptionRequest<UpdateGeoLocationResponse> mUpdateDescriptironRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void UpdateDescriptionRequestTest() throws Exception {
        mUpdateDescriptironRequest = new UpdateDescriptionRequest<>(mActivityRule.getActivity().getApplicationContext(),
                UpdateGeoLocationResponse.class);
        mUpdateDescriptironRequest.setListener(new Response.Listener<UpdateGeoLocationResponse>(){
            @Override
            public void onResponse(UpdateGeoLocationResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mUpdateDescriptironRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertFalse(true);
            }
        });
        mUpdateDescriptironRequest.execute();
    }

}
