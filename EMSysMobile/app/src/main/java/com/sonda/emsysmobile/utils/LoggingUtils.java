package com.sonda.emsysmobile.utils;

import com.sonda.emsysmobile.model.LoginResponse;

import java.util.List;

/**
 * Created by marccio on 9/27/16.
 */

public class LoggingUtils {

    /**
     * Imprime contenido de una LoginResponse.
     * @param response Response a imprimir.
     */
    public static void printLoginResponse(LoginResponse response) {
        List<LoginResponse.Rol> roles = response.getRoles();
        System.out.println("RESPONSE:\n"
                + "cod : " + response.getCodigoRespuesta() + "\n"
                + "access_token : " + response.getAccessToken() + "\n"
                + "expires_in : " + response.getExpirationTime() + "\n"
                + "roles : \n");
        for (LoginResponse.Rol rol : roles) {
            System.out.println("    tipo : " + rol.tipo);
            System.out.println("    id : " + rol.id);
        }
    }

}
