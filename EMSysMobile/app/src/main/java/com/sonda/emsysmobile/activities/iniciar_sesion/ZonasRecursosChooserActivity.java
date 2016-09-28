package com.sonda.emsysmobile.activities.iniciar_sesion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;

/**
 * Created by marccio on 9/28/16.
 */

public class ZonasRecursosChooserActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_recursos_chooser);

//        mUserEditText = (EditText) findViewById(R.id.input_username);
//
//        mPassEditText = (EditText) findViewById(R.id.input_password);
//        mPassEditText.setTypeface(Typeface.DEFAULT);
//        mPassEditText.setTransformationMethod(new PasswordTransformationMethod());
//
//        mLoginButton = (Button) findViewById(R.id.button_login);
//        mLoginButton.setOnClickListener(this);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.button_login) {
//            login();
//        }
    }

}
