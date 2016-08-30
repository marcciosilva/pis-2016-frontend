package pis16.memsysandroid.notifications.factories;

import pis16.memsysandroid.notifications.NotificationsPubNub;
import pis16.memsysandroid.notifications.NotificationsRealtime;
import pis16.memsysandroid.notifications.interfaces.INotificationReceiver;
import pis16.memsysandroid.notifications.interfaces.INotificationSystem;

/**
 * Created by marccio on 29-Aug-16.
 */
public class FactoryNotificationSystem {

    public enum PushNotificationsSystem {PubNub, RealtimeFramework}

    ;

    // Determina qué sistema se utiliza para push notifications.
    private static PushNotificationsSystem _currentNotificationSystem = PushNotificationsSystem.RealtimeFramework;

    public static INotificationSystem GetInstance(INotificationReceiver caller) {
        if (_currentNotificationSystem == PushNotificationsSystem.PubNub) {
            return new NotificationsPubNub(caller);
        } else if (_currentNotificationSystem == PushNotificationsSystem.RealtimeFramework) {
            return new NotificationsRealtime(caller);
        } else {
            return null;
        }
    }

}
