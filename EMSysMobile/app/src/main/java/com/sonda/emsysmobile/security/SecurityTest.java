package com.sonda.emsysmobile.security;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sonda.emsysmobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by marccio on 22-Sep-16.
 */
public class SecurityTest extends AsyncTask<Void, Void, Void>{

    private static final String TAG = SecurityTest.class.getName();
    private Context mContext;

    public SecurityTest(Context context) {
        mContext = context;
    }

    public static void copyInputStreamToOutputStream(final InputStream in,
                                                     final OutputStream out) throws IOException {
        try {
            try {
                final int size = 1024;
                final byte[] buffer = new byte[size];
                int n;
                while ((n = in.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    @Override
    protected final Void doInBackground(Void... params) {
        try {
            String keyStoreType = "BKS";
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            InputStream in = mContext.getResources().openRawResource(R.raw.keystore);
            try {
                // Se inicializa keystore con los certificados (de momento uno solo).
                // Se agrega password para la keystore.
                keyStore.load(in, "emsys22092016".toCharArray());
            } finally {
                in.close();
            }

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("https://10.0.3.2:44316");
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection) url.openConnection();
            urlConnection.setHostnameVerifier(new TrustAllHostNameVerifier());
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            in = urlConnection.getInputStream();
            copyInputStreamToOutputStream(in, System.out);
            Log.d(TAG, "Success my nigga");
        } catch (Exception e) {
            Log.d(TAG, "Exception");
            Log.d(TAG, e.getStackTrace().toString());
        }
        return null;
    }
}
