package com.sonda.emsysmobile.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.LoginResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserEditText;
    private EditText mPassEditText;
    private Button mLoginButton;
    private ProgressBar mProgressBar;

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
                //Se guarda el token en shared preferences para usar en cada consulta al web service.
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", response.getAccessToken()).commit();
                System.out.println("Token guardado en preferencias.");
                mProgressBar.setVisibility(View.GONE);
                goToHome();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error en el Login.");
                mProgressBar.setVisibility(View.GONE);
            }
        });
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}