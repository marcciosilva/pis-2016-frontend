package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.ExternalServiceResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.GetImageDataResponse;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;
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
public class GetImageDataRequestExecuteTest {

    private GetImageDataRequest<GetImageDataResponse> mGetImageDataRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void executeTest() throws Exception {
        mGetImageDataRequest = new GetImageDataRequest<>(mActivityRule.getActivity().getApplicationContext(),
                GetImageDataResponse.class);
        mGetImageDataRequest.setAttributes(1);
        mGetImageDataRequest.setListener(new Response.Listener<GetImageDataResponse>(){
            @Override
            public void onResponse(GetImageDataResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mGetImageDataRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertFalse(true);
            }
        });
        mGetImageDataRequest.execute();
    }
}
