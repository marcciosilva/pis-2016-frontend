package com.sonda.emsysmobile.activities.iniciar_sesion;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.sonda.emsysmobile.model.GetRolesResponse;
import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;
import com.sonda.emsysmobile.model.core.DtoZona;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sonda.emsysmobile.utils.JsonUtils.isSuccessfulResponse;
import static com.sonda.emsysmobile.utils.JsonUtils.getErrorMessage;

/**
 * Created by marccio on 9/28/16.
 */

public class RoleChooserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDespachadorButton;
    private Button mRecursoButton;
    private Button mSinSeleccionButton;
    private DtoRol mRoles;

    public enum EleccionRol {Despachador, Recurso;}

    private Bundle mExtras;
    private static final String TAG = RoleChooserActivity.class.getName();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_chooser);

        mDespachadorButton = (Button) findViewById(R.id.button_despachador);
        mDespachadorButton.setOnClickListener(this);
        mDespachadorButton.setEnabled(false);
        mRecursoButton = (Button) findViewById(R.id.button_recurso);
        mRecursoButton.setOnClickListener(this);
        mRecursoButton.setEnabled(false);
        mSinSeleccionButton = (Button) findViewById(R.id.button_sin_seleccion);
        mSinSeleccionButton.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        obtenerRoles(new VolleyCallback() {
            @Override
            public void onSuccess(DtoRol roles) {
                mRoles = roles;
                ArrayList<DtoZona> zonas = roles.getZonas();
                ArrayList<DtoRecurso> recursos = roles.getRecursos();
                boolean containsZona = zonas.size() > 0;
                mDespachadorButton.setEnabled(containsZona);
                boolean containsRecurso = recursos.size() > 0;
                mRecursoButton.setEnabled(containsRecurso);
            }
        });

    }

    private void obtenerRoles(final VolleyCallback callback) {
        GsonPostRequest<GetRolesResponse> request = RequestFactory.getRolesRequest(new Response.Listener<GetRolesResponse>() {
            @Override
            public void onResponse(GetRolesResponse response) {
                String codigoRespuestaString = response.getCodigoRespuesta();
                if (codigoRespuestaString != null) {
                    int codigoRespuesta = Integer.parseInt(codigoRespuestaString);
                    boolean getRolesExitoso = isSuccessfulResponse(codigoRespuesta);
                    if (getRolesExitoso) {
                        DtoRol roles = response.getRoles();
                        callback.onSuccess(roles);
                    } else {
                        String errorMsg = getErrorMessage(codigoRespuesta);
                        Log.d(TAG, "errorMsg : " + errorMsg);
                        //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                RoleChooserActivity.this,
                                android.R.style.Theme_Material_Light_Dialog_MinWidth);
                        builder.setTitle("Error");
                        builder.setMessage(errorMsg);
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                } else {
                    Log.d(TAG, "Error en el formato del mensaje recibido.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error en la comunicación con el servidor.");
            }
        }, getApplicationContext());
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    public interface VolleyCallback {
        void onSuccess(DtoRol result);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_despachador && mDespachadorButton.isEnabled()) {
            goToZonasRecursosChooser(EleccionRol.Despachador);
        } else if (view.getId() == R.id.button_recurso && mRecursoButton.isEnabled()) {
            goToZonasRecursosChooser(EleccionRol.Recurso);
        } else if (view.getId() == R.id.button_sin_seleccion) {
            goToHome();
        }
    }

    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void goToZonasRecursosChooser(EleccionRol eleccionRol) {
        Intent intent = new Intent(this, ZonasRecursosChooserActivity.class);
        // Paso data a la siguiente activity.
        if (eleccionRol == EleccionRol.Despachador) {
            intent.putExtra("zonas", mRoles.getZonas());
        } else if (eleccionRol == EleccionRol.Recurso){
            intent.putExtra("recursos", mRoles.getRecursos());
        }
        intent.putExtra("eleccionRol", eleccionRol);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
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
    public Action getIndexApiAction() {
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

}
