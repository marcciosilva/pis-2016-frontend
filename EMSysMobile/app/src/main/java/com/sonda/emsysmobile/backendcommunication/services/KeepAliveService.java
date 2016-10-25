package com.sonda.emsysmobile.backendcommunication.services;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.model.responses.KeepAliveResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.KeepAliveRequest;
import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class KeepAliveService extends Service {

    public static final String TAG = "KeepAliveService";

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
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    String successMsg = response.getInnerResponse().getMsg();
                    Log.d(TAG, successMsg);
                } else{
                    String errorMsg = response.getInnerResponse().getMsg();
                    handleErrorMessage(KeepAliveService.this, responseCode, errorMsg);
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
                handleVolleyErrorResponse(KeepAliveService.this, error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        keep_alive();
                    }
                });
            }
        });
        request.execute();
        //Log.d("STATE", "keep alive!!" );
    }
}