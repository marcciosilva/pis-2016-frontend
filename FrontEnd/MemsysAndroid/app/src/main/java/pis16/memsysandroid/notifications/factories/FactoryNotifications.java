package pis16.memsysandroid.notifications.factories;

import android.app.Activity;

import pis16.memsysandroid.notifications.NotificationsPubNub;
import pis16.memsysandroid.notifications.NotificationsRealtime;
import pis16.memsysandroid.notifications.interfaces.INotifications;
import pis16.memsysandroid.ui.MainActivity;

/**
 * Created by marccio on 29-Aug-16.
 */
public class FactoryNotifications {



    public enum PushNotificationsSystem { PubNub, RealtimeFramework };

    // Determina qué sistema se utiliza para push notifications.
    private static PushNotificationsSystem _currentNotificationSystem = PushNotificationsSystem.PubNub;

    public static INotifications GetInstance(MainActivity caller) {
        if (_currentNotificationSystem == PushNotificationsSystem.PubNub)
        {
            return new NotificationsPubNub(caller);
        }
        else if (_currentNotificationSystem == PushNotificationsSystem.RealtimeFramework)
        {
            return new NotificationsRealtime(caller);
        }
        else
        {
            return null;
        }
    }

}
