package com.sonda.emsysmobile.backendcommunication.offline;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;

import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;
import com.sonda.emsysmobile.utils.OfflineUtils;

import java.util.concurrent.BlockingQueue;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class OfflineService extends Service {

    public static final String TAG = OfflineService.class.getName();


    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        // Se crea un thread que se encarga de ejecutar el keep alive cada cierto tiempo.
        new Thread(new Runnable() {
            public void run() {
                BlockingQueue<OfflineAttachDescriptionDto> queue = OfflineUtils.getQueue();
                while (true) { // TODO es chancho pero funca...
                    // hacer get (o simil bloqueante) de la cola y procesarlo
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

}