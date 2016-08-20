package com.example.andres.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andres.myfirstapplication.ClienteHttp.UsuarioAPIClient;
import com.example.andres.myfirstapplication.Models.Usuario;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class DesplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplay_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);

        //prueba call asyncronico
        UsuarioAPIClient.UsuarioEndpointInterface apiService = UsuarioAPIClient.retrofit.create(UsuarioAPIClient.UsuarioEndpointInterface.class);

        String username = "Andres";
        Call<Usuario> call = apiService.getUser(username);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Response<Usuario> response) {
                Usuario user = response.body();
                String contraseña=user.getContraseña();
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });

        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
