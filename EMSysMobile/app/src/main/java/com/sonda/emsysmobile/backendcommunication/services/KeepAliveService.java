package com.sonda.emsysmobile.backendcommunication.services;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.sonda.emsysmobile.backendcommunication.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.KeepAliveResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ResponseCodeCategory;
import com.sonda.emsysmobile.backendcommunication.services.request.KeepAliveRequest;
import com.sonda.emsysmobile.logic.model.core.KeepAliveDto;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;
import com.sonda.emsysmobile.ui.activities.login.RoleChooserActivity;

import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class KeepAliveService extends Service {

    private boolean logged;
    // A definir waiting_time.
    private static int waiting_time = 5000;

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
                        keep_alive(new VolleyCallbackKeepAlive(){
                            @Override
                            public void onSuccess(KeepAliveDto keepAlive) {
                            }
                        });
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
    private void keep_alive(final VolleyCallbackKeepAlive callback) {
        KeepAliveRequest<KeepAliveResponse> request = new KeepAliveRequest<>(getApplicationContext(), KeepAliveResponse.class);
        request.setListener(new Response.Listener<KeepAliveResponse>() {
            @Override
            public void onResponse(KeepAliveResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == ResponseCodeCategory.SUCCESS.getNumVal()) {
                    KeepAliveDto keepAlive = response.getKeepAlive();
                    callback.onSuccess(keepAlive);
                } else {
                    /*
                    String errorMsg = response.getExpirationTime().getMsg();
                    handleErrorMessage(KeepAliveService.this, responseCode, errorMsg);
                    */
                }
            }
        });


        request.execute();
        Log.d("STATE", "keep alive!!" );
    }
    public interface VolleyCallbackKeepAlive {
        void onSuccess(KeepAliveDto result);
    }
}