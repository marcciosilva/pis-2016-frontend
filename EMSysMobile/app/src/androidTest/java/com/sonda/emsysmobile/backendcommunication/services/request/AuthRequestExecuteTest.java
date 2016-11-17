package com.sonda.emsysmobile.backendcommunication.services.request;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.AuthResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

/**
 * Created by Pape on 7/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class AuthRequestExecuteTest {

    private AuthRequest<AuthResponse> mAuthRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void AuthRequestTest() throws Exception {
        mAuthRequest = new AuthRequest<>(mActivityRule.getActivity().getApplicationContext(), AuthResponse.class);
        mAuthRequest.setAttributes("test_user", "test_pass");
        mAuthRequest.setListener(new Response.Listener<AuthResponse>(){
            @Override
            public void onResponse(AuthResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mAuthRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertFalse(true);
            }
        });
        mAuthRequest.execute();
    }

}
