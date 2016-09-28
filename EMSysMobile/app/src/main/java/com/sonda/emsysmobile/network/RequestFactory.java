package com.sonda.emsysmobile.network;

import android.content.Context;
import android.preference.PreferenceManager;

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

    public static final String LOGIN_PATH = "/users/authenticate";
    // Con el fin de probar cada caso de login con Mock Server:
    public static final String LOGIN_SUCCESS_PATH = "/users/success";
    public static final String LOGIN_USERNAME_FAIL_PATH = "/users/username-fail";
    public static final String LOGIN_PASSWORD_FAIL_PATH = "/users/pass-fail";

    private enum LoginCase {Success, UsernameFail, PassFail}

    private static final LoginCase loginCase = LoginCase.Success;

    public static JsonArrayRequest genericGETRequest(String url, Response.Listener<JSONArray> listener,
                                                     Response.ErrorListener errorListener) {
        return new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
    }

    public static GsonPostRequest<LoginResponse> loginRequest(String username, String password,
                                                              Response.Listener<LoginResponse> listener,
                                                              Response.ErrorListener errorListener,
                                                              Context context) {
        // Determino si estoy en modo debug de acuerdo a shared preferences.
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String url = null;
        if (!debugMode) {
            // Se envia request a la url de login que ofrece el web service.
            url = BuildConfig.BASE_URL + LOGIN_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (loginCase) {
                case Success:
                    // Siempre se obtiene una respuesta exitosa frente a login.
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_SUCCESS_PATH;
                    break;
                case UsernameFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_USERNAME_FAIL_PATH;
                    break;
                case PassFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_PASSWORD_FAIL_PATH;
                    break;
                default:
                    break;
            }

        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("username", username);
        System.out.println(jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!debugMode) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), LoginResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), LoginResponse.class, listener, errorListener);
        }
    }
}
