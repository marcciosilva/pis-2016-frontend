package com.sonda.emsysmobile.security;

/**
 * Created by marccio on 22-Sep-16.
 */
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TrustAllHostNameVerifier implements HostnameVerifier {

    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

}