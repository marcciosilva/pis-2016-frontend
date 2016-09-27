package com.sonda.emsysmobile.activities;

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
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;
import com.sonda.emsysmobile.utils.LoggingUtils;

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
                int codigoRespuesta = response.getCodigoRespuesta();
                String errorMsg = null;
                boolean loginExitoso = false;
                switch (codigoRespuesta) {
                    // Login exitoso.
                    case 0:
                        loginExitoso = true;
                        break;
                    case 1:
                        errorMsg = "Nombre de usuario no existe.";
                        break;
                    case 2:
                        errorMsg = "La clave es incorrecta.";
                        break;
                    default:
                        break;
                }
                if (loginExitoso) {
                    LoggingUtils.printLoginResponse(response);
                    //Se guarda el token en shared preferences para usar en cada consulta al web service.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", response.getAccessToken()).commit();
                    System.out.println("Token guardado en preferencias.");
                    mProgressBar.setVisibility(View.GONE);
                    goToHome();
                } else {
                    Log.d(TAG, "errorMsg : " + errorMsg);
                    mProgressBar.setVisibility(View.GONE);
                    //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, android.R.style.Theme_Material_Light_Dialog_MinWidth);
                    builder.setTitle("Error");
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error desconocido en el Login.");
                mProgressBar.setVisibility(View.GONE);
            }
        });
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }


    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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