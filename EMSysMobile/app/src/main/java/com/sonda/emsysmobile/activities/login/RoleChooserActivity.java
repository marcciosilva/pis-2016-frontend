package com.sonda.emsysmobile.activities.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.activities.HomeActivity;
import com.sonda.emsysmobile.model.core.ResourceDto;
import com.sonda.emsysmobile.model.core.RoleDto;
import com.sonda.emsysmobile.model.core.ZoneDto;
import com.sonda.emsysmobile.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.network.services.request.GetRolesRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marccio on 9/28/16.
 */

public class RoleChooserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDespachadorButton;
    private Button mRecursoButton;
    private RoleDto mRoles;

    public enum EleccionRol {Despachador, Recurso;}

    private static final String TAG = RoleChooserActivity.class.getName();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_chooser);

        mDespachadorButton = (Button) findViewById(R.id.button_despachador);
        mDespachadorButton.setOnClickListener(this);
        mDespachadorButton.setEnabled(false);
        mRecursoButton = (Button) findViewById(R.id.button_recurso);
        mRecursoButton.setOnClickListener(this);
        mRecursoButton.setEnabled(false);
        Button mSinSeleccionButton = (Button) findViewById(R.id.button_sin_seleccion);
        mSinSeleccionButton.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        obtenerRoles(new VolleyCallbackGetRoles() {
            @Override
            public void onSuccess(RoleDto roles) {
                mRoles = roles;
                // Habilito o deshabilito botones en base a los roles obtenidos en
                // respuestas a la request.
                List<ZoneDto> zonas = roles.getZones();
                List<ResourceDto> recursos = roles.getResources();
                boolean containsZona = zonas != null && zonas.size() > 0;
                mDespachadorButton.setEnabled(containsZona);
                boolean containsRecurso = recursos != null && recursos.size() > 0;
                mRecursoButton.setEnabled(containsRecurso);
            }
        });

    }

    private void obtenerRoles(final VolleyCallbackGetRoles callback) {
        GetRolesRequest<GetRolesResponse> request = new GetRolesRequest<>(getApplicationContext(), GetRolesResponse.class);
        request.setListener(new Response.Listener<GetRolesResponse>() {
            @Override
            public void onResponse(GetRolesResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    RoleDto roles = response.getRoles();
                    callback.onSuccess(roles);
                } else {
                    // Obtengo mensaje de error correspondiente al codigo.
                    String errorMsg = response.getRoles().getMsg();
                    Log.d(TAG, "errorMsg : " + errorMsg);
                    //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            RoleChooserActivity.this,
                            android.R.style.Theme_Material_Light_Dialog_MinWidth);
                    builder.setTitle("Error");
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // En caso de no estar autenticado, se vuelve a la activity
                            // de autenticacion.
                            if (responseCode == 2) {
                                goToAuth();
                            }
                        }
                    });
                    builder.show();
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error en la comunicación con el servidor.");
            }
        });
        request.execute();
    }

    @Override
    public final void onClick(View view) {
        if (view.getId() == R.id.button_despachador && mDespachadorButton.isEnabled()) {
            goToZonasRecursosChooser(EleccionRol.Despachador);
        } else if (view.getId() == R.id.button_recurso && mRecursoButton.isEnabled()) {
            goToZonasRecursosChooser(EleccionRol.Recurso);
        } else if (view.getId() == R.id.button_sin_seleccion) {
            goToHome();
        }
    }

    public final void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public final void goToAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        // Se saca la activity actual del back stack para mejorar experiencia del usuario.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToZonasRecursosChooser(EleccionRol eleccionRol) {
        Intent intent = new Intent(this, ZonasRecursosChooserActivity.class);
        // Paso data a la siguiente activity.
        if (eleccionRol == EleccionRol.Despachador) {
            intent.putExtra("zonas", (Serializable) mRoles.getZones());
        } else if (eleccionRol == EleccionRol.Recurso) {
            intent.putExtra("recursos", (Serializable) mRoles.getResources());
        }
        intent.putExtra("eleccionRol", eleccionRol);
        startActivity(intent);
    }

    @Override
    public final void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public final void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public final Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RoleChooser Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * Interfaz implementada para recibir el resultado de la request
     * de Volley una vez que finalice.
     */
    public interface VolleyCallbackGetRoles {
        void onSuccess(RoleDto result);
    }

}
