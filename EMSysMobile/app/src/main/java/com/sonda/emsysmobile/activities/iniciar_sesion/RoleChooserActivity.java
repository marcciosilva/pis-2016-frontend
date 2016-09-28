package com.sonda.emsysmobile.activities.iniciar_sesion;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.activities.HomeActivity;
import com.sonda.emsysmobile.model.core.DtoRol;

import java.util.ArrayList;

/**
 * Created by marccio on 9/28/16.
 */

public class RoleChooserActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Button mDespachadorButton;
    private Button mRecursoButton;
    private Button mSinSeleccionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_chooser);

        mDespachadorButton = (Button) findViewById(R.id.button_despachador);
        mDespachadorButton.setOnClickListener(this);
        mRecursoButton = (Button) findViewById(R.id.button_recurso);
        mRecursoButton.setOnClickListener(this);
        mSinSeleccionButton = (Button) findViewById(R.id.button_sin_seleccion);
        mSinSeleccionButton.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // Obtengo elementos pasados del intent.
        ArrayList<DtoRol> modelIntentRoles = (ArrayList<DtoRol>) getIntent().getSerializableExtra("modelIntentRoles");
        boolean containsZona = getIntent().getBooleanExtra("containsZona", false);
        mDespachadorButton.setEnabled(containsZona);
        boolean containsRecurso = getIntent().getBooleanExtra("containsRecurso", false);
        mRecursoButton.setEnabled(containsRecurso);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_despachador) {
            goToZonasRecursosChooser();
        } else if (view.getId() == R.id.button_recurso) {
            goToZonasRecursosChooser();
        } else if (view.getId() == R.id.button_sin_seleccion) {
            goToHome();
        }
    }

    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void goToZonasRecursosChooser() {
        Intent intent = new Intent(this, ZonasRecursosChooserActivity.class);
        startActivity(intent);
    }

}
