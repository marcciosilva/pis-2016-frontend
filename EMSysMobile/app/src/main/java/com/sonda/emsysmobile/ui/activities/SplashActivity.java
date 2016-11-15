package com.sonda.emsysmobile.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.offline.OfflineService;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpPreferences();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        final Handler handler = new Handler();
        final long delayMillis = 2000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToAuthActivity();
            }
        }, delayMillis);


        // Start Offline service.
        Intent intent = new Intent(SplashActivity.this, OfflineService.class);
        startService(intent);

    }

    private void setUpPreferences() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Log.d(TAG, "Clearing shared prefs...");
        //sharedPrefs.edit().clear().commit();
        final String backendUrl = "backendUrl";
        String currentBackendUrl = sharedPrefs.getString(backendUrl, null);
        String token = sharedPrefs.getString("access_token", "No hay un token guardado");
        Log.d(TAG, "TOKEN: " + token);
        // Si la url del backend no esta definida en preferencias, se setea en base a BuildConfig.
        if (currentBackendUrl == null) {
            Log.d(TAG, "Setting up preferences...");
            sharedPrefs.edit().putString(backendUrl, BuildConfig.BASE_URL).commit();
        }
    }

    private void goToAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
