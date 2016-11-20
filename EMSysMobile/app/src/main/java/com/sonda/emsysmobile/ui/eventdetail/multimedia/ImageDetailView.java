package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.managers.MultimediaManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDetailView extends AppCompatActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        // Se agrega la flecha de ir hacia atras.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final ImageView imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        Bundle extras = getIntent().getExtras();
        int imageId = extras.getInt("imageId");
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(ImageDetailView.this);
        String imageUrl = sharedPrefs.getString("backendUrl", BuildConfig.BASE_URL) +
                "/adjuntos/getimagedata?idImagen="
                + Integer.toString(imageId);

        Picasso.with(ImageDetailView.this)
                .load(imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(1024, 0)
                .into(imgAvatar, new Callback() {
                    @Override
                    public void onSuccess() {
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        imgAvatar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        findViewById(R.id.error_layout)
                                .setVisibility(View.VISIBLE);
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    }
                });
        // Se agrega un PhotoViewAttacher, que se encarga de toda la funcionalidad de zoom.
        // No borrar.
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(imgAvatar);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        // Se maneja la flecha de ir hacia atras.
        if (item.getItemId() == android.R.id.home) {
            // Cierra la Activity y vuelve a la Activity anterior (si la hubo).
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
