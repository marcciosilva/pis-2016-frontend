package pis16.memsysandroid.notifications.interfaces;

import java.util.ArrayList;

import pis16.memsysandroid.ui.MainActivity;

/**
 * Created by marccio on 29-Aug-16.
 */
public interface INotifications
{
    void SubscribeChannel(String channelName);
    void UnsubscribeChannel(String channelName);
    boolean isConnected();
}
