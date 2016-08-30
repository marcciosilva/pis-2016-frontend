package pis16.memsysandroid.notifications;

import ibt.ortc.api.Ortc;
import ibt.ortc.extensibility.OnConnected;
import ibt.ortc.extensibility.OnMessage;
import ibt.ortc.extensibility.OrtcClient;
import ibt.ortc.extensibility.OrtcFactory;
import pis16.memsysandroid.notifications.interfaces.INotificationReceiver;
import pis16.memsysandroid.notifications.interfaces.INotifications;
import pis16.memsysandroid.ui.MainActivity;

/**
 * Created by marccio on 29-Aug-16.
 */
public class NotificationsRealtime implements INotifications {

    private OrtcClient client;
    INotificationReceiver caller;
    String appKey = "HDzwHh";
    String token = "testToken";
    String googleProjectId = "757215377367";

    public NotificationsRealtime(final INotificationReceiver caller) {
        this.caller = caller;
        OrtcFactory factory;
        Ortc ortc = new Ortc();
        try {
            factory = ortc.loadOrtcFactory("IbtRealtimeSJ");
            client = factory.createClient();
//            client.setApplicationContext(caller.getApplicationContext());
            client.setGoogleProjectId(googleProjectId);
            client.setClusterUrl("http://ortc-developers.realtime.co/server/2.1/");
            client.onConnected = new OnConnected() {
                @Override
                public void run(OrtcClient sender) {
                    System.out.println("Cliente en Android conectado.");
                }
            };
            client.connect(appKey, token);
        } catch (Exception e) {
            System.out.println(String.format("Realtime Error: %s", e.toString()));
        }
    }

    @Override
    public void SubscribeChannel(String channelName) {
        client.subscribe(channelName, true,
                new OnMessage() {
                    // Esta función maneja los mensajes recibidos en el canal
                    // al que el cliente esté suscrito.
                    public void run(OrtcClient sender, String channel, String message) {
                        // Received a message
                        System.out.println(channel + " > " + message);
                        caller.onMessageReceived(message);
                    }
                });
        System.out.println("Cliente suscrito al canal " + channelName);
    }

    @Override
    public void UnsubscribeChannel(String channelName) {
        client.unsubscribe(channelName);
    }


    @Override
    public  void SendMessage(String channelName, String message) {

    }

    @Override
    public boolean isConnected() {
        return client.getIsConnected();
    }


}

