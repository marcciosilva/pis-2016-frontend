package com.sonda.emsysmobile.backendcommunication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sonda.emsysmobile.BuildConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by ssainz on 8/28/16.
 */
public final class AppRequestQueue {

    private static AppRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    private static final String TAG = AppRequestQueue.class.getName();

    private AppRequestQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized AppRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            if (BuildConfig.BASE_URL.contains("https")) {
                HurlStack hurlStack = new HurlStack() {
                    @Override
                    protected HttpURLConnection createConnection(URL url) throws IOException {
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                        try {
                            TrustManager[] trustAllCerts = new TrustManager[]{
                                    new X509TrustManager() {
                                        public X509Certificate[] getAcceptedIssuers() {
                                            return new X509Certificate[0];
                                        }

                                        @Override
                                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                                        }

                                        @Override
                                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                                        }
                                    }
                            };
                            SSLContext sc = SSLContext.getInstance("SSL");
                            sc.init(null, trustAllCerts, new SecureRandom());
                            httpsURLConnection.setSSLSocketFactory(sc.getSocketFactory());
                            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String arg0, SSLSession arg1) {
                                    return true;
                                }
                            });


                        } catch (KeyManagementException e) {
                            Log.d(TAG, e.getStackTrace().toString());
                        } catch (NoSuchAlgorithmException e) {
                            Log.d(TAG, e.getStackTrace().toString());
                        }
                        return httpsURLConnection;
                    }
                };
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), hurlStack);
            } else {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
