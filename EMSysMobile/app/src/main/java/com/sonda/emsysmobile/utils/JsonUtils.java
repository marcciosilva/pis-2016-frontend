package com.sonda.emsysmobile.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * Created by marccio on 08-Sep-16.
 */
public class JsonUtils {

    public static String jsonToUrlEncodedString(JsonObject jsonObject) {
        return jsonToUrlEncodedString(jsonObject, "");
    }

    public static String jsonToUrlEncodedString(JsonObject jsonObject, String prefix) {
        String urlString = "";
        for (Map.Entry<String, JsonElement> item : jsonObject.entrySet()) {
            if (item.getValue() != null && item.getValue().isJsonObject()) {
                urlString += jsonToUrlEncodedString(
                        item.getValue().getAsJsonObject(),
                        prefix.isEmpty() ? item.getKey() : prefix + "[" + item.getKey() + "]"
                );
            } else {
                urlString += prefix.isEmpty() ?
                        item.getKey() + "=" + item.getValue().getAsString() + "&" :
                        prefix + "[" + item.getKey() + "]=" + item.getValue().getAsString() + "&";
            }
        }
        return urlString;
    }

    public static boolean isSuccessfulResponse(int codigoRespuesta) {
        switch (codigoRespuesta) {
            case 0:
                return true;
            default:
                return false;
        }
    }

    public static String getErrorMessage(int codigoRespuesta) {
        if (codigoRespuesta == 1) {
            return "Credenciales no válidas.";
        }
        return "";
    }

}
