package com.sonda.emsysmobile.backendcommunication.services.request;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.EventsResponse;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pape on 8/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class EventsRequestExecuteTest {

    private EventsRequest<EventsResponse> mEventsRequest;

    //Obtengo el context de la app a partir de una activity
    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void EventsRequestsTest() throws Exception {
        mEventsRequest = new EventsRequest<>(mActivityRule.getActivity().getApplicationContext(), EventsResponse.class);
        mEventsRequest.setListener(new Response.Listener<EventsResponse>(){
            @Override
            public void onResponse(EventsResponse response) {
                int responseCode = response.getCode();
                assertEquals(responseCode, ErrorCodeCategory.SUCCESS.getNumVal());
            }
        });
        mEventsRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mEventsRequest.execute();
    }
}
