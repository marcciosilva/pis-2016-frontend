package com.sonda.emsysmobile.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.activities.login.AuthActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        final long delayMillis = 2000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToAuthActivity();
            }
        }, delayMillis);
    }

    private void goToAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
