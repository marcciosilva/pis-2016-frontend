package com.sonda.emsysmobile.activities.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.sonda.emsysmobile.activities.HomeActivity;
import com.sonda.emsysmobile.model.responses.AuthResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserEditText;
    private EditText mPassEditText;
    private Button mLoginButton;
    private static final String TAG = AuthActivity.class.getName();
    private ProgressBar mProgressBar;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(this);
        mLoginButton.setEnabled(false);

        mUserEditText = (EditText) findViewById(R.id.input_username);
        mUserEditText.addTextChangedListener(new TextWatcher()  {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateLoginButton(mLoginButton, mUserEditText);
            }

            @Override
            public void afterTextChanged(Editable s)  {
                updateLoginButton(mLoginButton, mUserEditText);
            }
        });

        mPassEditText = (EditText) findViewById(R.id.input_password);
        mPassEditText.setTypeface(Typeface.DEFAULT);
        mPassEditText.setTransformationMethod(new PasswordTransformationMethod());
        mPassEditText.addTextChangedListener(new TextWatcher()  {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateLoginButton(mLoginButton, mPassEditText);
            }

            @Override
            public void afterTextChanged(Editable s)  {
                updateLoginButton(mLoginButton, mPassEditText);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void updateLoginButton(Button mLoginButton, EditText editText) {
        if (!checkEmptyEditText(editText)) {
            mLoginButton.setEnabled(true);
        } else {
            mLoginButton.setEnabled(false);
        }
    }

    private boolean checkEmptyEditText(EditText editText) {
        if (editText.getText().toString().length() <= 0) {
            editText.setError("Este campo no puede estar vacío.");
            return true;
        } else {
            editText.setError(null);
            return false;
        }
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
        GsonPostRequest<AuthResponse> request = RequestFactory.authRequest(user, pass, new Response.Listener<AuthResponse>() {
            @Override
            public void onResponse(AuthResponse response) {
                int responseCode = response.getCode();
                if (responseCode == 0) {
                    //Se guarda el token en shared preferences para usar en cada consulta al web service.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", response.getAccessToken()).commit();
                    Log.d(TAG, "Token guardado en preferencias.");
                    goToRoleChooser();
                } else {
                    String errorMsg = response.getInnerResponse().getMsg();
                    Log.d(TAG, "errorMsg : " + errorMsg);
                    mProgressBar.setVisibility(View.GONE);
                    //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this, android.R.style.Theme_Material_Light_Dialog_MinWidth);
                    builder.setTitle("Error");
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error en la comunicación con el servidor.");
                mProgressBar.setVisibility(View.GONE);
            }
        }, getApplicationContext());
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }



    /**
     * Metodo utilizado para pasar desde la activity de login hacia
     * la de eleccion del rol del usuario.
     */
    private void goToRoleChooser() {
        Intent intent = new Intent(this, RoleChooserActivity.class);
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