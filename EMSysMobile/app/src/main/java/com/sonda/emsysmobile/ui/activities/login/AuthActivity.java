package com.sonda.emsysmobile.ui.activities.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.AuthResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.backendcommunication.services.request.AuthRequest;
import com.sonda.emsysmobile.backendcommunication.services.request.SendNotificationTokenRequest;
import com.sonda.emsysmobile.logic.model.core.UserDto;
import com.sonda.emsysmobile.notifications.MyFirebaseInstanceIDService;
import com.sonda.emsysmobile.ui.activities.SettingsActivity;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;
import static com.sonda.emsysmobile.utils.UIUtils.hideSoftKeyboard;

public class AuthActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = AuthActivity.class.getName();
    private EditText mUserEditText;
    private EditText mPassEditText;
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

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        ImageButton configButton = (ImageButton) findViewById(R.id.button_config);
        configButton.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public final void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
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
    public final void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public final void onClick(View view) {
        if ((view.getId() == R.id.button_login) && (validLogin())) {
            login();
        } else if (view.getId() == R.id.button_config) {
            goToConfig();
        }
    }

    private void goToConfig() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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
        hideSoftKeyboard(this);
        final String user = mUserEditText.getText().toString();
        final String pass = mPassEditText.getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);

        AuthRequest<AuthResponse> authRequest =
                new AuthRequest<>(getApplicationContext(), AuthResponse.class);
        authRequest.setAttributes(user, pass);
        authRequest.setListener(new Response.Listener<AuthResponse>() {
            @Override
            public void onResponse(AuthResponse response) {
                int responseCode = response.getCode();
                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
                    // Se guarda un UserDto con las credenciales del usuario.
                    // Este dto sera completado con mas informacion en las activities posteriores.
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user);
                    userDto.setPassword(pass);
                    GlobalVariables.setUserData(userDto);
                    //Se guarda el token en shared preferences para usar en cada consulta al web
                    // service.
                    SharedPreferences.Editor prefsEditor =
                            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                    prefsEditor.putString("access_token", response.getAccessToken());
                    prefsEditor.apply();
                    Log.d(TAG, "Token guardado en preferencias.");
                    sendRegistrationToServer();
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    String errorMsg = response.getInnerResponse().getMsg();
                    if (!isFinishing()) {
                        handleErrorMessage(AuthActivity.this, responseCode, errorMsg);
                    }
                }
            }
        });
        authRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, getString(R.string.error_http));
                if (!isFinishing()) {
                    handleVolleyErrorResponse(AuthActivity.this, error, new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            login();
                        }
                    });
                }
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendRegistrationToServer() {
        String token = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(MyFirebaseInstanceIDService.NOTIFICATION_TOKEN_KEY, null);
        if (token != null) {
            SendNotificationTokenRequest<EmsysResponse> request =
                    new SendNotificationTokenRequest<>(this, EmsysResponse.class);
            request.setToken(token);
            request.setListener(new Response.Listener<EmsysResponse>() {
                @Override
                public void onResponse(EmsysResponse response) {
                    Log.d(TAG, "Notifications token registered");
                    goToRoleChooser();
                }
            });
            request.setErrorListener(new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error when registering Notifications token: " + error.getMessage());
                    goToRoleChooser();
                }
            });
            request.execute();
        } else {
            goToRoleChooser();
        }
    }
}