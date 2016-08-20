package com.example.andres.myfirstapplication.ClienteHttp;

import com.example.andres.myfirstapplication.Models.Usuario;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Andres on 20/08/2016.
 */
public class UsuarioAPIClient {
    public static final String BASE_URL = "http://pis-prototipo1-api.azurewebsites.net/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public  interface UsuarioEndpointInterface {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("api/Usuario/{username}")
        Call<Usuario> getUser(@Path("username") String username);

        @GET("api/Usuario")
        Call<List<Usuario>> getUsuarios();

        @POST("users/new")
        Call<Usuario> createUser(@Body Usuario user);
    }
}

