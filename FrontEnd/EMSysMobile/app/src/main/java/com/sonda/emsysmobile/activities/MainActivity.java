package com.sonda.emsysmobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sonda.emsysmobile.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mGoToAppButton;
    private Button mConsumeWSButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoToAppButton = (Button) findViewById(R.id.button_goto_app);
        mGoToAppButton.setOnClickListener(this);
        mConsumeWSButton = (Button) findViewById(R.id.button_consume_ws);
        mConsumeWSButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_goto_app) {
            goToLoginActivity();
        } else if (view.getId() == R.id.button_consume_ws){
            goToConsumeWSActivity();
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToConsumeWSActivity () {
        Intent intent = new Intent(this, ConsumeWSActivity.class);
        startActivity(intent);
    }
}