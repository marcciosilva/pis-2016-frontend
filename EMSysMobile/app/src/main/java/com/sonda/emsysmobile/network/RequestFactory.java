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
    // Con el fin de probar cada caso de login con Mock Server:
    public static final String LOGIN_SUCCESS_PATH = "/users/success";
    public static final String LOGIN_USERNAME_FAIL_PATH = "/users/username-fail";
    public static final String LOGIN_PASSWORD_FAIL_PATH = "/users/pass-fail";
    private static final boolean mDebug = true;
    private enum LoginCase {Success, UsernameFail, PassFail}
    private static final LoginCase loginCase = LoginCase.Success;

    public static JsonArrayRequest genericGETRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        return new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
    }

    public static GsonPostRequest<LoginResponse> loginRequest(String username, String password, Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener) {
        String url = null;
        if (!mDebug) {
            url = BuildConfig.BASE_URL + LOGIN_PATH;
        } else {
            switch (loginCase) {
                case Success:
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_SUCCESS_PATH;
                    break;
                case UsernameFail:
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_USERNAME_FAIL_PATH;
                    break;
                case PassFail:
                url = BuildConfig.BASE_MOCK_URL + LOGIN_PASSWORD_FAIL_PATH;
                break;
                default:
                    break;
            }

        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("grant_type", "password");
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("username", username);
        System.out.println(jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!mDebug) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), LoginResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), LoginResponse.class, listener, errorListener);
        }
    }
}
