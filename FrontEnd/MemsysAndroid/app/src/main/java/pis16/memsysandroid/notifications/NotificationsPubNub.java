package pis16.memsysandroid.notifications;

import java.util.ArrayList;

import pis16.memsysandroid.notifications.interfaces.INotifications;
import pis16.memsysandroid.ui.MainActivity;

/**
 * Created by marccio on 29-Aug-16.
 */
public class NotificationsPubNub implements INotifications {


    @Override
    public void SubscribeChannel(String channelName) {

    }

    @Override
    public void UnsubscribeChannel(String channelName) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

}
