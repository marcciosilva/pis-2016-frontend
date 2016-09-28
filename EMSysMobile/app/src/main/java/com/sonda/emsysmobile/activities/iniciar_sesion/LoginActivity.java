package com.sonda.emsysmobile.activities.iniciar_sesion;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.LoginResponse;
import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;
import com.sonda.emsysmobile.model.core.DtoZona;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;
import com.sonda.emsysmobile.utils.LoggingUtils;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserEditText;
    private EditText mPassEditText;
    private Button mLoginButton;
    private ProgressBar mProgressBar;
    private static final String TAG = LoginActivity.class.getName();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserEditText = (EditText) findViewById(R.id.input_username);

        mPassEditText = (EditText) findViewById(R.id.input_password);
        mPassEditText.setTypeface(Typeface.DEFAULT);
        mPassEditText.setTransformationMethod(new PasswordTransformationMethod());

        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_login) {
            login();
        }
    }

    private void login() {
        String user = mUserEditText.getText().toString();
        String pass = mPassEditText.getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);
        GsonPostRequest<LoginResponse> request = RequestFactory.loginRequest(user, pass, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                String codigoRespuestaString = response.getCodigoRespuesta();
                if (codigoRespuestaString != null) {
                    int codigoRespuesta = Integer.parseInt(codigoRespuestaString);
                    boolean loginExitoso = isSuccessfulResponse(codigoRespuesta);
                    if (loginExitoso) {
                        LoggingUtils.printLoginResponse(response);
                        //Se guarda el token en shared preferences para usar en cada consulta al web service.
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", response.getAccessToken()).commit();
                        Log.d(TAG, "Token guardado en preferencias.");
                        mProgressBar.setVisibility(View.GONE);
                        // Lista (serializable) a pasar en el intent que pasa a la siguiente
                        // actividad. Dicha lista se compone de roles pasados al modelo de datos
                        // manejado desde el Frontend.
                        ArrayList<DtoRol> modelIntentRoles = new ArrayList<>();
                        // Booleanos a agregar al intent, para que la activity siguiente
                        // sepa si la lista de roles pasados en el mismo tiene zonas/recursos.
                        boolean containsZona = false;
                        boolean containsRecurso = false;
                        for (LoginResponse.Rol rol : response.getRoles()) {
                            if (rol.tipo.equals("zona")) {
                                containsZona = true;
                                modelIntentRoles.add(new DtoZona(rol.id));
                            } else if (rol.tipo.equals("recurso")) {
                                containsRecurso = true;
                                modelIntentRoles.add(new DtoRecurso(rol.id));
                            }
                        }
                        goToRoleChooser(modelIntentRoles, containsZona, containsRecurso);
                    } else {
                        String errorMsg = getErrorMessage(codigoRespuesta);
                        Log.d(TAG, "errorMsg : " + errorMsg);
                        mProgressBar.setVisibility(View.GONE);
                        //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, android.R.style.Theme_Material_Light_Dialog_MinWidth);
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
                Log.d(TAG, "Error desconocido en el Login.");
                mProgressBar.setVisibility(View.GONE);
            }
        }, getApplicationContext());
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    /**
     * Metodo utilizado para pasar desde la activity de login hacia
     * la de eleccion del rol del usuario.
     *
     * @param modelIntentRoles Lista con modelos correspondientes a roles.
     * @param containsZona Indica si la lista contiene zonas.
     * @param containsRecurso Indica si la lista contiene recursos.
     */
    private void goToRoleChooser(ArrayList<DtoRol> modelIntentRoles, boolean containsZona, boolean containsRecurso) {
        Intent intent = new Intent(this, RoleChooserActivity.class);
        intent.putExtra("modelIntentRoles", modelIntentRoles);
        intent.putExtra("containsZona", containsZona);
        intent.putExtra("containsRecurso", containsRecurso);
        startActivity(intent);
    }

    private String getErrorMessage(int codigoRespuesta) {
        if (codigoRespuesta == 1) {
            return "Nombre de usuario no existe.";
        } else {
            return "La clave es incorrecta.";
        }
    }

    private boolean isSuccessfulResponse(int codigoRespuesta) {
        switch (codigoRespuesta) {
            // Login exitoso.
            case 0:
                return true;
            // Login fallido.
            default:
                return false;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
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
}