package com.sonda.emsysmobile.ui.activities.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.AuthResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ResponseCodeCategory;
import com.sonda.emsysmobile.backendcommunication.services.request.AuthRequest;
import com.sonda.emsysmobile.utils.UIUtils;

import java.net.HttpURLConnection;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;

public class AuthActivity extends FragmentActivity implements View.OnClickListener {

    private EditText mUserEditText;
    private EditText mPassEditText;
    private static final String TAG = AuthActivity.class.getName();
    private ProgressBar mProgressBar;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(this);

        mUserEditText = (EditText) findViewById(R.id.input_username);

        mPassEditText = (EditText) findViewById(R.id.input_password);
        mPassEditText.setTypeface(Typeface.DEFAULT);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public final void onClick(View view) {
        if ((view.getId() == R.id.button_login) && (validLogin())) {
                login();
        }
    }

    private boolean validLogin() {
        if (mUserEditText.getText().toString().isEmpty()) {
            mUserEditText.setError(getResources().getString(R.string.error_required_username));
            return false;
        } else if (mPassEditText.getText().toString().isEmpty()) {
            mPassEditText.setError(getResources().getString(R.string.error_required_password));
            return false;
        }
        return true;
    }

    private void login() {
        String user = mUserEditText.getText().toString();
        String pass = mPassEditText.getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);

        AuthRequest<AuthResponse> authRequest = new AuthRequest<>(getApplicationContext(),AuthResponse.class);
        authRequest.setAttributes(user, pass);
        authRequest.setListener(new Response.Listener<AuthResponse>(){
            @Override
            public void onResponse(AuthResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ResponseCodeCategory.SUCCESS.getNumVal()) {
                    //Se guarda el token en shared preferences para usar en cada consulta al web service.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", response.getAccessToken()).commit();
                    Log.d(TAG, "Token guardado en preferencias.");
                    goToRoleChooser();
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    String errorMsg = response.getInnerResponse().getMsg();
                    handleErrorMessage(AuthActivity.this, responseCode, errorMsg);
                }
            }
        });
        authRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
            }
        });
        authRequest.execute();
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
    public final Action getIndexApiAction() {
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
}