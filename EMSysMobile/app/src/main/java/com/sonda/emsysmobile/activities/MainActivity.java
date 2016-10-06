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
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.activities.login.AuthActivity;
import com.sonda.emsysmobile.persistence.DatabaseTest;
import com.sonda.emsysmobile.security.SecurityTest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private Button mGoToAppButton;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        // Agregar preferencia de modo debug.
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putBoolean("debugMode", BuildConfig.USING_MOCK_SERVER).commit();

        mGoToAppButton = (Button) findViewById(R.id.button_goto_app);
        mGoToAppButton.setOnClickListener(this);
        Button consumeWSButton = (Button) findViewById(R.id.button_consume_ws);
        consumeWSButton.setOnClickListener(this);
        Button logNotificationsTokenButton = (Button) findViewById(R.id.button_log_notifications_token);
        logNotificationsTokenButton.setOnClickListener(this);
        Button testDatabaseButton = (Button) findViewById(R.id.button_test_database);
        testDatabaseButton.setOnClickListener(this);
        Button mTestSsl = (Button) findViewById(R.id.button_test_ssl);
        mTestSsl.setOnClickListener(this);

    }

    @Override
    public final void onClick(View view) {
        if (view.getId() == R.id.button_goto_app) {
            goToAuthActivity();
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
        } else if (view.getId() == R.id.button_test_ssl) {
//            System.out.println("USER LOCATION: " + System.getProperty("user.dir"));
            SecurityTest securityTest = new SecurityTest(getApplicationContext());
            securityTest.execute();
        }
    }

    private void goToAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void goToConsumeWSActivity () {
        Intent intent = new Intent(this, ConsumeWSActivity.class);
        startActivity(intent);
    }
}