package com.example.andres.myfirstapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andres.myfirstapplication.ClienteHttp.UsuarioAPIClient;
import com.example.andres.myfirstapplication.Models.Usuario;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class DesplayMessageActivity extends AppCompatActivity {

    private Context context;
    private UsuarioAPIClient.UsuarioEndpointInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplay_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);

        this.context = this;

        //prueba call asyncronico
        apiService = UsuarioAPIClient.retrofit.create(UsuarioAPIClient.UsuarioEndpointInterface.class);

        new AsyncTaskPrototipo().execute();


        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }

    private class AsyncTaskPrototipo extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... args) {
            String username = "Andres";

            Call<Usuario> call = apiService.getUser(username);

            try {
                Response<Usuario> response = call.execute();
                Usuario user = response.body();
                Log.i("INFO", user.getNombreUsuario() + " con clave : " + user.getContraseña());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void empty) {
            Toast.makeText(context, "Fin de lista alcanzado", Toast.LENGTH_LONG).show();
        }

    }
}
