package com.sonda.emsysmobile.network.services;

import android.content.Context;

import com.android.volley.Response;
import com.sonda.emsysmobile.model.responses.AuthResponse;
import com.sonda.emsysmobile.network.services.request.AuthRequest;


/**
 * Created by mserralta on 1/10/16.
 */
public class LoginService {

    private Context context;

    public LoginService(Context context){
        this.context = context;
    }

    public final Response<AuthResponse> login(String user, String password){
        AuthRequest<AuthResponse> authRequest = new AuthRequest<>(context,AuthResponse.class);
        authRequest.setAttributes(user, password);
        authRequest.setListener(new Response.Listener<AuthResponse>(){
            @Override
            public void onResponse(AuthResponse response) {

            }
        });


        return null;
    }
}
