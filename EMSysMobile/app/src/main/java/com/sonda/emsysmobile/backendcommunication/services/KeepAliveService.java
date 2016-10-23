package com.sonda.emsysmobile.backendcommunication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Response;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.KeepAliveResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.KeepAliveRequest;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class KeepAliveService extends Service {

    private boolean logged;
    // A definir waiting_time.
    private static int waiting_time = 60000;

    @Override
    public void onCreate() {
        logged = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable(){
            public void run() {
                while(logged)
                {
                    try {
                        Thread.sleep(waiting_time);
                        if(logged){
                            keep_alive();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        logged = false;
    }

    private void keep_alive() {
        KeepAliveRequest<KeepAliveResponse> request = new KeepAliveRequest<>(getApplicationContext(), KeepAliveResponse.class);
        request.setListener(new Response.Listener<KeepAliveResponse>() {
            @Override
            public void onResponse(KeepAliveResponse response) {
                final int responseCode = response.getCode();
                if (responseCode != ErrorCodeCategory.SUCCESS.getNumVal()) {
                    handleErrorMessage(KeepAliveService.this, responseCode, errorMsg);
                }
            }
        });
        response.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
                handleVolleyErrorResponse(KeepAliveService.this, error);
            }
        });
        request.execute();
        //Log.d("STATE", "keep alive!!" );
    }
}