package com.sonda.emsysmobile.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.SendNotificationTokenRequest;

/**
 * Created by ssainz on 9/5/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String NOTIFICATION_TOKEN_KEY = "notif_token";
    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public final void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        saveToken(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void saveToken(String token) {
        String oldToken = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(MyFirebaseInstanceIDService.NOTIFICATION_TOKEN_KEY, null);
        if (oldToken == null) {
            //First time, save token and send it when user login
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            sharedPrefs.edit().putString(NOTIFICATION_TOKEN_KEY, token).apply();
        } else {
            SendNotificationTokenRequest<EmsysResponse> request =
                    new SendNotificationTokenRequest<>(this, EmsysResponse.class);
            request.setToken(token);
            request.setListener(new Response.Listener<EmsysResponse>() {
                @Override
                public void onResponse(EmsysResponse response) {
                    Log.d(TAG, "Notifications token registered");
                }
            });
            request.setErrorListener(new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error when registering Notifications token: " + error.getMessage());
                }
            });
            request.execute();
        }
    }
}
