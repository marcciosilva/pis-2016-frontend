package com.sonda.emsysmobile.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.model.LoginResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sonda.emsysmobile.utils.JsonUtils.jsonToUrlEncodedString;

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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("grant_type", "password");
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("username", username);
        System.out.println(jsonObject.toString());
        //Hay que mandar el string url encoded.
        return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), LoginResponse.class, listener, errorListener);
    }
}
