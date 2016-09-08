package com.sonda.emsysmobile.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.LoginResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

import java.util.HashMap;
import java.util.Map;

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
//        GsonPostRequest<LoginResponse> request = RequestFactory.loginRequest(user, pass, new Response.Listener<LoginResponse>() {
//            @Override
//            public void onResponse(LoginResponse response) {
//                mProgressBar.setVisibility(View.GONE);
//                goToHome();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mProgressBar.setVisibility(View.GONE);
//            }
//        });
//        AppRequestQueue.getInstance(this).addToRequestQueue(request);
        HttpPOSTRequestWithParameters();
    }

    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void HttpPOSTRequestWithParameters() {
//        RequestQueue queue = AppRequestQueue.getInstance(this).getRequestQueue();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.31:2956/oauth2/token";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            // Seteo de parametros
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                //TODO reemplazar por username pasado desde interfaz
                params.put("username", "administrator");
                //TODO reemplazar por password pasada desde interfaz
                params.put("password", "administrator123");

                return params;
            }
        };
        queue.add(postRequest);
    }


}