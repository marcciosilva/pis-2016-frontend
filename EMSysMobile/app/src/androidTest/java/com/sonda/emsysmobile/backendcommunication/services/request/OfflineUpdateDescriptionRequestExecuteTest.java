package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.UserDto;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Pape on 11/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class OfflineUpdateDescriptionRequestExecuteTest {

    private OfflineUpdateDescriptionRequest<EmsysResponse> mOfflineUpdateDescRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void AuthRequestTest() throws Exception {
        OfflineAttachDescriptionDto offlineAttachDescriptionDto = new OfflineAttachDescriptionDto();
        UserDto userDto = new UserDto();
        offlineAttachDescriptionDto.setDescription("test_descriptiron");
        offlineAttachDescriptionDto.setTimeStamp(new Date());
        offlineAttachDescriptionDto.setExtensionId(1);
        offlineAttachDescriptionDto.setUserData(userDto);
        mOfflineUpdateDescRequest = new OfflineUpdateDescriptionRequest<>(mActivityRule.getActivity().getApplicationContext(),
                EmsysResponse.class, offlineAttachDescriptionDto);
        mOfflineUpdateDescRequest.setListener(new Response.Listener<EmsysResponse>(){
            @Override
            public void onResponse(EmsysResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mOfflineUpdateDescRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertFalse(true);
            }
        });
        mOfflineUpdateDescRequest.execute();
    }
}
