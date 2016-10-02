package com.sonda.emsysmobile.services.endpoint;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.model.responses.AuthResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;

import java.lang.reflect.Type;
import java.util.Map;

import static com.sonda.emsysmobile.BuildConfig.BASE_MOCK_URL;
import static com.sonda.emsysmobile.utils.JsonUtils.jsonToUrlEncodedString;

/**
 * Created by mserralta on 29/9/16.
 */
public class EndpointService<T> {

    private Context context;
    private String accesToken;


    public EndpointService(Context context){
        this.context = context;
    }

    public void execute(int method, String path, JsonObject jsonObject, Type type, Response.Listener listener, Response.ErrorListener errorListener) {
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);

        String url;
        if (!debugMode) {
            url = BuildConfig.BASE_URL;
        } else {
            url = BASE_MOCK_URL;
        }



        url += path;
        System.out.println(jsonObject.toString());
        String accesToken = getAccesToken();

        if(accesToken != ""){
            jsonObject.addProperty("authorization",accesToken);
        }

        Log.d("IP_REQUEST", url);
        GsonPostRequest<T> request;
        if(!debugMode){
            request = new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), type, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            request =  new GsonPostRequest<>(url, jsonObject.toString(), type, listener, errorListener);
        }
        AppRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    private String getAccesToken(){
        if (accesToken == null) {
            accesToken = PreferenceManager.
                    getDefaultSharedPreferences(context).getString("access_token", "");
        }
        return accesToken;

    }

}
