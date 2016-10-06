package com.sonda.emsysmobile.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

/**
 * Created by ssainz on 8/28/16.
 */
public final class RequestFactory {

    private RequestFactory() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static JsonArrayRequest genericGETRequest(String url, Response.Listener<JSONArray> listener,
                                                     Response.ErrorListener errorListener) {
        return new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
    }

}
