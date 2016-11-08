package com.sonda.emsysmobile.backendcommunication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.KeepAliveResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.KeepAliveRequest;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class KeepAliveService extends Service {

    public static final String TAG = KeepAliveService.class.getName();
    private boolean logged;
    public static int waitingTime = BuildConfig.WAITING_TIME;

    @Override
    public final void onCreate() {
        logged = true;
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        // Se crea un thread que se encarga de ejecutar el keep alive cada cierto tiempo.
        new Thread(new Runnable() {
            public void run() {
                while (logged) {
                    try {
                        Thread.sleep(waitingTime);
                        if (logged) {
                            keepAlive();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public final void onDestroy() {
        logged = false;
    }

    private void keepAlive() {
        KeepAliveRequest<KeepAliveResponse> request =
                new KeepAliveRequest<>(getApplicationContext(), KeepAliveResponse.class);
        request.setListener(new Response.Listener<KeepAliveResponse>() {
            @Override
            public void onResponse(KeepAliveResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    Log.d(TAG, "Exito.");
                } else {
                    Log.d(TAG, "Error!");
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
            }
        });
        request.execute();
    }
}