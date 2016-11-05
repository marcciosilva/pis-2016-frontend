package com.sonda.emsysmobile.backendcommunication.offline;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineDto;

import java.util.concurrent.BlockingQueue;

/**
 * Created by nachoprbd on 21/10/2016.
 */
public class OfflineService extends Service {

    public static final String TAG = OfflineService.class.getName();
    private BlockingQueue<OfflineDto> queue;
    private boolean running = false;
    private Thread mainThread;
    private final int WAIT_TIME = 1000;

    @Override
    public void onCreate() {
        running = true;
        queue = GlobalVariables.getQueue();
        Log.d(TAG, "Servicio offline iniciado.");
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        // Se crea un thread que se encarga de ejecutar el keep alive cada cierto tiempo.
        mainThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        // Se obtiene un elemento de la cola. El thread queda bloqueado esperando
                        // que aparezca un elemento para tomar.
                        OfflineDto offlineDto = queue.take();
                        // Espero a que la aplicacion vuelva a estar online.
                        while (!GlobalVariables.isOfflineMode()) {
                            Thread.sleep(WAIT_TIME);
                        }
                        if (offlineDto instanceof OfflineAttachDescriptionDto) {
                            Log.d(TAG, "Hay un offlineAttachDesc bla para mandar");
                        }
                        // TODO soportar otros offline dtos.
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    // hacer get (o simil bloqueante) de la cola y procesarlo
                }
                Log.d(TAG, "Servicio offline terminado.");
            }
        });
        mainThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void addOfflineDescription(OfflineAttachDescriptionDto offlineAttachDescriptionDto) {
        try {
            queue.put(offlineAttachDescriptionDto);
        } catch (InterruptedException e) {
            mainThread.interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mainThread.interrupt();
    }
}