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
import com.sonda.emsysmobile.model.responses.EventsResponse;
import com.sonda.emsysmobile.model.responses.AuthResponse;
import com.sonda.emsysmobile.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.model.core.RoleDto;

import org.json.JSONArray;

import static com.sonda.emsysmobile.BuildConfig.*;
import static com.sonda.emsysmobile.utils.JsonUtils.jsonToUrlEncodedString;

/**
 * Created by ssainz on 8/28/16.
 */
public class RequestFactory {


    private enum AuthCase {Success, CredentialsFail, AlreadyAuth}

    private static final AuthCase authCase = AuthCase.Success;


    private enum GetRolesCase {Both, Recursos, Zonas, Fail, Empty}

    private static final GetRolesCase getRolesCase = GetRolesCase.Both;


    private enum LoginCase {Success, Fail}

    private static final LoginCase loginCase = LoginCase.Success;


    private enum LogoutCase {Success, Cod2, Cod5}

    private static final LogoutCase logoutCase = LogoutCase.Success;

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
            url = BASE_URL + AUTH_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (authCase) {
                case Success:
                    // Siempre se obtiene una respuesta exitosa frente a login.
                    url = BASE_MOCK_URL + AUTH_SUCCESS_PATH;
                    break;
                case CredentialsFail:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    url = BASE_MOCK_URL + AUTH_CREDENTIALS_FAIL;
                    break;
                case AlreadyAuth:
                    // Siempre se obtiene una respuesta fallida frente a login.
                    url = BASE_MOCK_URL + AUTH_ALREADY;
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
            url = BASE_URL + GET_ROLES_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (getRolesCase) {
                case Both:
                    url = BASE_MOCK_URL + GET_ROLES_BOTH_PATH;
                    break;
                case Fail:
                    url = BASE_MOCK_URL + GET_ROLES_FAIL_PATH;
                    break;
                case Recursos:
                    url = BASE_MOCK_URL + GET_ROLES_RECURSOS_PATH;
                    break;
                case Zonas:
                    url = BASE_MOCK_URL + GET_ROLES_ZONAS_PATH;
                    break;
                case Empty:
                    url = BASE_MOCK_URL + GET_ROLES_EMPTY_PATH;
                    break;
                default:
                    break;
            }

        }
        JsonObject jsonObject = new JsonObject();
        // Agrego token de usuario.
        jsonObject.addProperty("authorization", getAuthToken(context));
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

    public static GsonGetRequest<EventsResponse> eventsRequest(Response.Listener<EventsResponse> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL + EVENTS_PATH;
        return new GsonGetRequest<>(url, EventsResponse.class, listener, errorListener);
    }

    public static GsonPostRequest<LoginLogoutResponse> loguearUsuarioRequest(
            RoleDto roles,
            Response.Listener<LoginLogoutResponse> listener,
            Response.ErrorListener errorListener,
            Context context) {
        // Determino si estoy en modo debug de acuerdo a shared preferences.
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String url = null;
        if (!debugMode) {
            // Se envia request a la url de getRoles que ofrece el web service.
            url = BASE_URL + LOGIN_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (loginCase) {
                case Success:
                    url = BASE_MOCK_URL + LOGIN_SUCCESS_PATH;
                    break;
                case Fail:
                    url = BASE_MOCK_URL + LOGIN_FAIL_PATH;
                    break;
                default:
                    break;
            }

        }
        // Se prepara el mensaje a enviar.
        String json = new Gson().toJson(roles);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        // Agrego token de usuario.
        jsonObject.addProperty("authorization", getAuthToken(context));
        // Se imprime string en json.
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!debugMode) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), LoginLogoutResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), LoginLogoutResponse.class, listener, errorListener);
        }
    }


    public static GsonPostRequest<LoginLogoutResponse> logoutRequest(
            Response.Listener<LoginLogoutResponse> listener,
            Response.ErrorListener errorListener,
            Context context) {
        // Determino si estoy en modo debug de acuerdo a shared preferences.
        boolean debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debugMode", false);
        String url = null;
        if (!debugMode) {
            // Se envia request a la url de getRoles que ofrece el web service.
            url = BASE_URL + LOGOUT_PATH;
        } else {
            // Se utilizan web services del mock server con respuestas fijas.
            switch (logoutCase) {
                case Success:
                    url = BASE_MOCK_URL + LOGOUT_SUCCESS_PATH;
                    break;
                case Cod2:
                    url = BASE_MOCK_URL + LOGOUT_COD_2_PATH;
                    break;
                case Cod5:
                    url = BASE_MOCK_URL + LOGOUT_COD_5_PATH;
                    break;
                default:
                    break;
            }
        }
        // Se prepara el mensaje a enviar.
        JsonObject jsonObject = new JsonObject();
        // Agrego token de usuario.
        jsonObject.addProperty("authorization", getAuthToken(context));
        // Se imprime string en json.
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        //Hay que mandar el string url encoded.
        if (!debugMode) {
            return new GsonPostRequest<>(url, jsonToUrlEncodedString(jsonObject), LoginLogoutResponse.class, listener, errorListener);
        } else {
            // En el mock server no se exige un string url encoded.
            return new GsonPostRequest<>(url, jsonObject.toString(), LoginLogoutResponse.class, listener, errorListener);
        }
    }

    private static String getAuthToken(Context context) {
        return "Bearer " + PreferenceManager.
                getDefaultSharedPreferences(context).getString("access_token", "");
    }

}
