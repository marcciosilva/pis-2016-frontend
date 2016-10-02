package com.sonda.emsysmobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;

import static com.sonda.emsysmobile.utils.JsonUtils.getErrorMessage;
import static com.sonda.emsysmobile.utils.JsonUtils.jsonToUrlEncodedString;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class JsonUtilsUnitTest {

    @Test
    public void testGetErrorMessage_1() {
        // Testeo que el mensaje de error sea el correcto.
        assertTrue(getErrorMessage(1).equals("Credenciales no válidas."));
    }

    @Test
    public void testGetErrorMessage_2() {
        // Testeo que el mensaje de error sea el correcto.
        assertTrue(getErrorMessage(2).equals("El usuario no se encuentra autenticado."));
    }

    @Test
    public void testGetErrorMessage_3() {
        // Testeo que el mensaje de error sea el correcto.
        assertTrue(getErrorMessage(3).equals("Se ha seleccionado más de un recurso."));
    }

    @Test
    public void testGetErrorMessage_4() {
        // Testeo que el mensaje de error sea el correcto.
        assertTrue(getErrorMessage(4).equals("Se han seleecionado zonas y recursos a la vez."));
    }

    @Test
    public void testGetErrorMessage_other() {
        // Testeo que el mensaje de error sea el correcto.
        assertTrue(getErrorMessage(6).equals(""));
    }

    @Test
    public void testJsonToUrlEncodedString() {
        // Testeo si el formato al que se convierte el String es correcto.
        String json = "{\"cod\": 2,\"response\":{\"msg\":\"Usuario no autenticado.\"}}";
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        String urlEncodedString = jsonToUrlEncodedString(jsonObject);
        assertTrue(urlEncodedString.equals("cod=2&response[msg]=Usuario no autenticado.&"));
    }
}
