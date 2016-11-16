package com.sonda.emsysmobile.ui.eventdetail.multimedia;

/**
 * Created by marccio on 11/16/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sonda.emsysmobile.GlobalVariables;
import com.sonda.emsysmobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static com.sonda.emsysmobile.GlobalVariables.KEYSTORE_PASSWORD;

public final class CustomOkHttpClient {

    private static final String TAG = CustomOkHttpClient.class.getName();

    private CustomOkHttpClient() {
        // Es privado porque no debe utilizarse.
    }

    public static OkHttpClient getCustomOkHttpClient(Context context) {
        HostnameVerifier hostnameVerifier = null;
        SSLSocketFactory sslSocketFactory = null;
        if (!GlobalVariables.USING_CUSTOM_KEYSTORE) {
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] certs,
                                                           String authType) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs,
                                                           String authType) {
                            }

                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                };
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                sslSocketFactory = sc.getSocketFactory();
                hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
            } catch (NoSuchAlgorithmException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            } catch (KeyManagementException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            }
        } else {
            try {
                KeyStore keyStore = KeyStore.getInstance(GlobalVariables.KEYSTORE_TYPE);
                InputStream in =
                        context.getResources().openRawResource(R.raw.keystore);
                try {
                    // Se inicializa keystore con los certificados.
                    // Se agrega password para la keystore.
                    keyStore.load(in, KEYSTORE_PASSWORD.toCharArray());
                } finally {
                    in.close();
                }
                // Se crea un TrustManager que confia en todas las CAs de nuestro
                // keystore.
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf =
                        TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // Se crea un SSLContext que usa nuestro TrustManager.
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, tmf.getTrustManagers(), null);
                // La URLConnection usa una SocketFactory de nuestro SSLContext.
                sslSocketFactory = sc.getSocketFactory();
            } catch (CertificateException e) {
                Log.d(TAG, e.getStackTrace().toString());
            } catch (NoSuchAlgorithmException e) {
                Log.d(TAG, e.getStackTrace().toString());
            } catch (KeyStoreException e) {
                Log.d(TAG, e.getStackTrace().toString());
            } catch (KeyManagementException e) {
                Log.d(TAG, e.getStackTrace().toString());
            } catch (IOException e) {
                Log.d(TAG, e.getStackTrace().toString());
            }
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        // Se agrega el token como header.
        final String authToken = sharedPrefs.getString("access_token", "");
        OkHttpClient okHttpClient = null;
        if (hostnameVerifier == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("auth", authToken)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .sslSocketFactory(sslSocketFactory)
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("auth", authToken)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(sslSocketFactory)
                    .build();
        }
        return okHttpClient;
    }
}