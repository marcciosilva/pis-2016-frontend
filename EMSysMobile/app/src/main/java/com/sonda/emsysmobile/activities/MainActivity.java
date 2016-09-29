package com.sonda.emsysmobile.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.activities.iniciar_sesion.AuthActivity;
import com.sonda.emsysmobile.persistence.DatabaseTest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private Button mGoToAppButton;
    private Button mConsumeWSButton;
    private Button mLogNotificationsTokenButton;
    private Button mTestDatabaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        // Agregar preferencia de modo debug.
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putBoolean("debugMode", true).commit();

        mGoToAppButton = (Button) findViewById(R.id.button_goto_app);
        mGoToAppButton.setOnClickListener(this);
        mConsumeWSButton = (Button) findViewById(R.id.button_consume_ws);
        mConsumeWSButton.setOnClickListener(this);
        mLogNotificationsTokenButton = (Button) findViewById(R.id.button_log_notifications_token);
        mLogNotificationsTokenButton.setOnClickListener(this);
        mTestDatabaseButton = (Button) findViewById(R.id.button_test_database);
        mTestDatabaseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_goto_app) {
            goToLoginActivity();
        } else if (view.getId() == R.id.button_consume_ws){
            goToConsumeWSActivity();
        } else if (view.getId() == R.id.button_log_notifications_token) {
            // Get token
            String token = FirebaseInstanceId.getInstance().getToken();

            // Log and toast
            String msg = getString(R.string.msg_token_fmt, token);
            Log.d(TAG, msg);
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.button_test_database) {
            DatabaseTest.executeTest();
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void goToConsumeWSActivity () {
        Intent intent = new Intent(this, ConsumeWSActivity.class);
        startActivity(intent);
    }
}