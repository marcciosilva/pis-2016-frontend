package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.AppRequestQueue;

public class ImageTest extends AppCompatActivity {

    private static final String TAG = ImageTest.class.getName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        NetworkImageView imgAvatar = (NetworkImageView) findViewById(R.id.imgAvatar);
        ImageLoader imageLoader = AppRequestQueue.getInstance(ImageTest.this).getImageLoader();
        Bundle extras = getIntent().getExtras();
        String imageUrl = extras.getString("imageUrl");
        if (imageUrl != null) {
            imgAvatar.setImageUrl(imageUrl, imageLoader);
        }
    }

}
