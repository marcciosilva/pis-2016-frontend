package pis16.memsysandroid.notifications;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import pis16.memsysandroid.notifications.interfaces.INotificationReceiver;
import pis16.memsysandroid.notifications.interfaces.INotifications;

/**
 * Created by marccio on 29-Aug-16.
 */
public class NotificationsPubNub implements INotifications {

    private static Pubnub pubnub;
    private String PublishKey = "pub-c-a024c6ed-96da-40cd-8e1a-45279bd4b63b";
    private String SuscribeKey = "sub-c-7d3f5b56-6e54-11e6-9259-0619f8945a4f";
    INotificationReceiver caller;

    public NotificationsPubNub(final INotificationReceiver caller) {
        this.caller = caller;
        pubnub = new Pubnub(PublishKey, SuscribeKey);

    }

    @Override
    public void SubscribeChannel(String channelName) {
        try {
            pubnub.subscribe(channelName, new Callback() {
                        @Override
                        public void connectCallback(String channelName, Object message) {
                            pubnub.publish(channelName, "Hello from the PubNub Java SDK", new Callback() {
                            });
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
                                    + " : "
                                    + message.toString());
                        }

                        public void reconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                                    + " : "
                                    + message.toString());
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : " + channel + " : "
                                    + message.toString());
                            caller.onMessageReceived(message.toString());
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
                                    + " : " + error.toString());
                        }
                    }
            );
        } catch (PubnubException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void SendMessage(String channelName, String message) {
        Callback callback = new Callback() {
            public void successCallback(String channel, Object response) {
                System.out.println(response.toString());
            }

            public void errorCallback(String channel, PubnubError error) {
                System.out.println(error.toString());
            }
        };
        pubnub.publish(channelName, message, callback);
    }

    @Override
    public void UnsubscribeChannel(String channelName) {
        pubnub.unsubscribe(channelName);
    }

    @Override
    public boolean isConnected() {
        Callback callback = new Callback() {
            public void successCallback(String channel, Object response) {
                System.out.println(response.toString());
            }

            public void errorCallback(String channel, PubnubError error) {
                System.out.println(error.toString());
            }
        };
        pubnub.time(callback);
        return true;
    }


}
