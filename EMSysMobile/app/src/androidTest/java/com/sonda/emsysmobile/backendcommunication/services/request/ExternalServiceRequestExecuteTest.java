package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.ExternalServiceResponse;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class ExternalServiceRequestExecuteTest {

    private ExternalServiceRequest<ExternalServiceResponse> mExternalServiceRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void ExternalServiceExecuteTest() throws Exception {
        ExternalServiceQueryDto queryDto = new ExternalServiceQueryDto("test_parameter_1", "test_parameter_2", "test_parameter_3");
        mExternalServiceRequest = new ExternalServiceRequest<>(mActivityRule.getActivity().getApplicationContext(),
                ExternalServiceResponse.class, queryDto);
        mExternalServiceRequest.setListener(new Response.Listener<ExternalServiceResponse>(){
            @Override
            public void onResponse(ExternalServiceResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mExternalServiceRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mExternalServiceRequest.execute();
    }
}
