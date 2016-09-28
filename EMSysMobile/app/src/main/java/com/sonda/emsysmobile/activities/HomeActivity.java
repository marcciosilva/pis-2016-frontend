package com.sonda.emsysmobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.EventsResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.RequestFactory;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getEvents();
    }

    private void getEvents() {
        Request eventsRequest = RequestFactory.eventsRequest(new Response.Listener<EventsResponse>() {
            @Override
            public void onResponse(EventsResponse response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
            }
        });
        AppRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(eventsRequest);
    }
}