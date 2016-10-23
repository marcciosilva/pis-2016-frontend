package com.sonda.emsysmobile.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.activities.MainActivity;

/**
 * Created by ssainz on 9/5/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String NOTIFICATION_KEY = "notification";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public final void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            String notificationCode = remoteMessage.getData().get("code");
            int objectIdentifier = Integer.parseInt(remoteMessage.getData().get("primarykey"));
            Notification notification = new Notification(notificationCode, objectIdentifier);
            Log.d(TAG, notification.toString());
            postApplicationEvent(notification);
//            return;
        }

        showNotificationOnStatusBar("EMSYS Mobile", "Tienes una nueva notificación");
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void showNotificationOnStatusBar(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /**
     * Events are generated to notify changes to others app's modules.
     * These events are posted through a Broadcast Receiver.
     *
     * @param notification Contains info about the notification that arrives.
     */
    private void postApplicationEvent(Notification notification) {
        NotificationsEvents event = notification.getEventFromCode();
        if (event != NotificationsEvents.NONE) {
            Log.d(TAG, "Posting app event: " + event.toString());
            Intent intent = new Intent(event.toString());
            intent.putExtra(NOTIFICATION_KEY, notification);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}