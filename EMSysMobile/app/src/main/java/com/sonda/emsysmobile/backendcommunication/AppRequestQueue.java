package com.sonda.emsysmobile.backendcommunication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import static com.sonda.emsysmobile.GlobalVariables.KEYSTORE_PASSWORD;
import static com.sonda.emsysmobile.GlobalVariables.USING_CUSTOM_KEYSTORE;

/**
 * Created by ssainz on 8/28/16.
 */
public final class AppRequestQueue {

    private static final String TAG = AppRequestQueue.class.getName();
    private static AppRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

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
                }) {
            @Override
            protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth,
                                                       int maxHeight, ImageView.ScaleType scaleType,
                                                       final String cacheKey) {
                return new ImageRequest(requestUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        onGetImageSuccess(cacheKey, response);
                    }
                }, maxWidth, maxHeight, scaleType, Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                onGetImageError(cacheKey, error);
                            }
                        });
            }
        };
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            if (BuildConfig.BASE_URL.contains("https")) {
                HurlStack hurlStack = new HurlStack() {
                    @Override
                    protected HttpURLConnection createConnection(URL url) throws IOException {
                        HttpsURLConnection httpsURLConnection =
                                (HttpsURLConnection) super.createConnection(url);
                        if (!USING_CUSTOM_KEYSTORE) {
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
                        } else {
                            try {
                                String keyStoreType = "BKS";
                                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                                InputStream in =
                                        mCtx.getResources().openRawResource(R.raw.keystore);
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
                                SSLContext context = SSLContext.getInstance("TLS");
                                context.init(null, tmf.getTrustManagers(), null);

                                // La URLConnection usa una SocketFactory de nuestro SSLContext.
                                httpsURLConnection.setSSLSocketFactory(context.getSocketFactory());
                            } catch (CertificateException e) {
                                Log.d(TAG, e.getStackTrace().toString());
                            } catch (NoSuchAlgorithmException e) {
                                Log.d(TAG, e.getStackTrace().toString());
                            } catch (KeyStoreException e) {
                                Log.d(TAG, e.getStackTrace().toString());
                            } catch (KeyManagementException e) {
                                Log.d(TAG, e.getStackTrace().toString());
                            }
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

    public static synchronized AppRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppRequestQueue(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
