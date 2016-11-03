package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDetailView extends AppCompatActivity {

    private static final String TAG = ImageDetailView.class.getName();

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
        if (imageUrl != null) {
            Picasso.with(ImageDetailView.this)
                    .load(imageUrl)
                    .into(imgAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                            imgAvatar.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onError() {
                            findViewById(R.id.error_layout)
                                    .setVisibility(View.VISIBLE);
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }
                    });
            // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
            // (not needed unless you are going to change the drawable later)
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(imgAvatar);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se maneja la flecha de ir hacia atras.
        if (item.getItemId() == android.R.id.home) {
            // Cierra la Activity y vuelve a la Activity anterior (si la hubo).
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
