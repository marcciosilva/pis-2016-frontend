package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Pape on 8/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class LoginRequestExecuteTest {

    private LoginRequest<LoginLogoutResponse> mLoginRequest;
    private RoleDto mRolDto;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void LoginRequestTest() throws Exception {
        ZoneDto zoneDto = new ZoneDto("test_name", 1, "test_name");
        List<ZoneDto> listZones = new ArrayList<ZoneDto>();
        listZones.add(zoneDto);
        ResourceDto resourceDto = new ResourceDto("test_code", 1);
        List<ResourceDto> listResources = new ArrayList<ResourceDto>();
        listResources.add(resourceDto);
        mRolDto = new RoleDto(listZones, listResources);
        mLoginRequest = new LoginRequest<>(mActivityRule.getActivity().getApplicationContext(),
                LoginLogoutResponse.class, mRolDto);
        mLoginRequest.setListener(new Response.Listener<LoginLogoutResponse>(){
            @Override
            public void onResponse(LoginLogoutResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mLoginRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertFalse(true);
            }
        });
        mLoginRequest.execute();
    }
}
