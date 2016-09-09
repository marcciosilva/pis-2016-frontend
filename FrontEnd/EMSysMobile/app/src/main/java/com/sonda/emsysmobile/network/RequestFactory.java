package com.sonda.emsysmobile.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.model.LoginResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ssainz on 8/28/16.
 */
public class RequestFactory {

    public static final String LOGIN_PATH = "/oauth2/token";

    public static JsonArrayRequest genericGETRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        return new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
    }

    public static GsonPostRequest<LoginResponse> loginRequest(String username, String password, Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener) {
        String url = BuildConfig.BASE_URL + LOGIN_PATH;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("grant_type", "password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("KASJDLKJDLKJWQ123123");
        System.out.println(jsonObject.toString());
        return new GsonPostRequest<>(url, jsonObject.toString(), LoginResponse.class, listener, errorListener);
    }
}
