package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class GetRolesRequestExecuteTest {

    private GetRolesRequest<GetRolesResponse> mGetRolesRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void executeTest() throws Exception {
        mGetRolesRequest = new GetRolesRequest<>(mActivityRule.getActivity().getApplicationContext(),
                GetRolesResponse.class);
        mGetRolesRequest.setListener(new Response.Listener<GetRolesResponse>(){
            @Override
            public void onResponse(GetRolesResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mGetRolesRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mGetRolesRequest.execute();
    }
}
