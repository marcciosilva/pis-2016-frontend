package com.sonda.emsysmobile.managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.notifications.NotificationsEvents;

import java.util.ArrayList;

/**
 * Created by sasainz on 11/7/16.
 */

public class NotificationsManager {


    private static final String TAG = NotificationsManager.class.getSimpleName();
    private static final String NOTIFICATION_KEY = "notification";
    private static final String NOTIFICATION_RECEIVED = "notification_received";

    private static NotificationsManager mInstance;
    private Context mContext;

    private ArrayList<Notification> notifications;

    private NotificationsManager(Context context) {
        notifications = new ArrayList<>();
        mContext = context;
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(broadcastReceiverNotifications,
                        new IntentFilter(NotificationsEvents.UPDATE_EVENTS_LIST.toString()));
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(broadcastReceiverNotifications,
                        new IntentFilter(NotificationsEvents.UPDATE_ONE_EVENT.toString()));
    }

    public static synchronized NotificationsManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotificationsManager(context);
        }
        return mInstance;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Catches notification events posted by MyFirebaseMessagingService
     */
    private BroadcastReceiver broadcastReceiverNotifications = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                Notification notification = (Notification) intent.getExtras().get(NOTIFICATION_KEY);
                if (notification != null) {
                    notification.setRead(false);
                    notifications.add(0, notification);
                    Intent newNotifIntent = new Intent(NOTIFICATION_RECEIVED);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(newNotifIntent);
                }
            }
        }
    };
}
