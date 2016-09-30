package com.sonda.emsysmobile.network;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.model.responses.AuthResponse;
import com.sonda.emsysmobile.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.model.responses.LoginResponse;
import com.sonda.emsysmobile.model.core.DtoRol;

import org.json.JSONArray;

import static com.sonda.emsysmobile.utils.JsonUtils.jsonToUrlEncodedString;

/**
 * Created by ssainz on 8/28/16.
 */
public class RequestFactory {

    public static final String AUTH_PATH = "/users/authenticate";
    public static final String GET_ROLES_PATH = "/users/getroles";
    public static final String LOGIN_PATH = "/users/login";
    // Con el fin de probar cada caso de auth con Mock Server:
    public static final String AUTH_SUCCESS_PATH = "/users/success";
    public static final String AUTH_USERNAME_FAIL_PATH = "/users/username-fail";
    public static final String AUTH_PASSWORD_FAIL_PATH = "/users/pass-fail";

    private enum AuthCase {Success, UsernameFail, PassFail}

    private static final AuthCase authCase = AuthCase.Success;
    // Con el fin de probar cada caso de getRoles con Mock Server:
    public static final String GET_ROLES_BOTH_PATH = "/users/getroles-both";
    public static final String GET_ROLES_FAIL_PATH = "/users/getroles-fail";
    public static final String GET_ROLES_RECURSOS_PATH = "/users/getroles-recursos";
    public static final String GET_ROLES_ZONAS_PATH = "/users/getroles-zonas";
    public static final String GET_ROLES_EMPTY_PATH = "/users/getroles-empty";

    private enum GetRolesCase {Both, Recursos, Zonas, Fail, Empty}

    private static final GetRolesCase getRolesCase = GetRolesCase.Both;
    // Con el fin de probar cada caso de login con Mock Server:
    public static final String LOGIN_SUCCESS_PATH = "/users/login-success";
    public static final String LOGIN_FAIL_PATH = "/users/login-fail";

    private enum LoginCase {Success, Fail}

    private static final LoginCase loginCase = LoginCase.Success;

    private static final String TAG = RequestFactory.class.getName();


    public static JsonArrayRequest genericGETRequest(String url, Response.Listener<JSONArray> listener,
                                                     Response.ErrorListener errorListener) {
        return new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
    }

    public static GsonPostRequest<AuthResponse> authRequest(String username, String password,
                                                            Response.Listener<AuthResponse> listener,
                                                            Response.ErrorListener errorListener,
                                                            Context context) {
        // Determino si estoy en modo debug de acuerdo a shared preferences.
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String url = null;
        if (!debugMode) {
            // Se envia request a la url de login que ofrece el web service.
            url = BuildConfig.BASE_URL + AUTH_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (authCase) {
                case Success:
                    // Siempre se obtiene una respuesta exitosa frente a login.
                    url = BuildConfig.BASE_MOCK_URL + AUTH_SUCCESS_PATH;
                    break;
                case UsernameFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    url = BuildConfig.BASE_MOCK_URL + AUTH_USERNAME_FAIL_PATH;
                    break;
                case PassFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    url = BuildConfig.BASE_MOCK_URL + AUTH_PASSWORD_FAIL_PATH;
                    break;
                default:
                    break;
            }

        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("username", username);
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!debugMode) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), AuthResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), AuthResponse.class, listener, errorListener);
        }
    }

    public static GsonPostRequest<GetRolesResponse> getRolesRequest(Response.Listener<GetRolesResponse> listener,
                                                                    Response.ErrorListener errorListener,
                                                                    Context context) {
        // Determino si estoy en modo debug de acuerdo a shared preferences.
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String url = null;
        if (!debugMode) {
            // Se envia request a la url de getRoles que ofrece el web service.
            url = BuildConfig.BASE_URL + GET_ROLES_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (getRolesCase) {
                case Both:
                    url = BuildConfig.BASE_MOCK_URL + GET_ROLES_BOTH_PATH;
                    break;
                case Fail:
                    url = BuildConfig.BASE_MOCK_URL + GET_ROLES_FAIL_PATH;
                    break;
                case Recursos:
                    url = BuildConfig.BASE_MOCK_URL + GET_ROLES_RECURSOS_PATH;
                    break;
                case Zonas:
                    url = BuildConfig.BASE_MOCK_URL + GET_ROLES_ZONAS_PATH;
                    break;
                case Empty:
                    url = BuildConfig.BASE_MOCK_URL + GET_ROLES_EMPTY_PATH;
                    break;
                default:
                    break;
            }

        }
        JsonObject jsonObject = new JsonObject();
        // Agrego token de usuario.
        jsonObject.addProperty("authorization", PreferenceManager.
                getDefaultSharedPreferences(context).getString("access_token", ""));
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!debugMode) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), GetRolesResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), GetRolesResponse.class, listener, errorListener);
        }
    }


    public static GsonPostRequest<LoginResponse> loguearUsuarioRequest(
            DtoRol roles,
            Response.Listener<LoginResponse> listener,
            Response.ErrorListener errorListener,
            Context context) {
        // Determino si estoy en modo debug de acuerdo a shared preferences.
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String url = null;
        if (!debugMode) {
            // Se envia request a la url de getRoles que ofrece el web service.
            url = BuildConfig.BASE_URL + LOGIN_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (loginCase) {
                case Success:
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_SUCCESS_PATH;
                    break;
                case Fail:
                    url = BuildConfig.BASE_MOCK_URL + LOGIN_FAIL_PATH;
                    break;
                default:
                    break;
            }

        }
        // Se prepara el mensaje a enviar.
        String json = new Gson().toJson(roles);
        json = "{\"roles\":" + json + "}";
        json = json.replace("recursos", "recurso");
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        // Agrego token de usuario.
        jsonObject.addProperty("authorization", PreferenceManager.
                getDefaultSharedPreferences(context).getString("access_token", ""));
        // Se imprime string en json.
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!debugMode) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), LoginResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), LoginResponse.class, listener, errorListener);
        }
    }


}
