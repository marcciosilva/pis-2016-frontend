package com.sonda.emsysmobile.backendcommunication.offline;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.backendcommunication.model.responses.OfflineAttachDescriptionResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.OfflineUpdateDescriptionRequest;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineDto;

import java.util.concurrent.BlockingQueue;

/**
 * Created by marccio on 21/10/2016.
 */
public class OfflineService extends Service {

    public static final String TAG = OfflineService.class.getName();
    private BlockingQueue<OfflineDto> queue;
    private Thread mainThread;
    private final int WAIT_TIME = 10000;
    private boolean offline = false;

    @Override
    public void onCreate() {
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
                        final OfflineDto offlineDto = queue.take();
                        // Espero en caso de no tener conexion espero.
                        // En caso contrario, se siguen enviando OfflineDtos de la cola.
                        if (offline) {
                            Thread.sleep(WAIT_TIME);
                        }
                        if (offlineDto instanceof OfflineAttachDescriptionDto) {
                            sendDescription((OfflineAttachDescriptionDto) offlineDto);
                        }
                        Log.d(TAG, "Intentando enviar datos en servicio offline...");
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getStackTrace().toString());
                    }
                }
            }
        });
        mainThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendDescription(final OfflineAttachDescriptionDto offlineDto) {
        OfflineUpdateDescriptionRequest<OfflineAttachDescriptionResponse>
                request =
                new OfflineUpdateDescriptionRequest<>(OfflineService.this,
                        OfflineAttachDescriptionResponse.class,
                        (OfflineAttachDescriptionDto) offlineDto);
        request.setListener(new Response
                .Listener<OfflineAttachDescriptionResponse>() {
            @Override
            public void onResponse(OfflineAttachDescriptionResponse response) {
                Log.d(TAG, "Envio realizado con exito");
                offline = false;
                // No debería hacerse nada más, ya que la request fue recibida
                // correctamente por el servidor, independientemente del
                // código que nos devuelva.
                // Sólo hay que manejar el caso de error de red.
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    // Si no hay conexion.
                    offline = true;
                    // Se vuelve a agregar el dto a la cola.
                    queue.put(offlineDto);
                } catch (InterruptedException e) {
                    Log.d(TAG, e.getStackTrace().toString());
                }
            }
        });
        request.execute();
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