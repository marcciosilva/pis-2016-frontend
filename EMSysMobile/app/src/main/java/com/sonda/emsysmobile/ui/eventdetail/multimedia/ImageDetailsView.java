package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonda.emsysmobile.R;

/**
 * Created by marccio on 28-Oct-16.
 */
public class ImageDetailsView extends AppCompatActivity {

    private static final String TAG = ImageDetailsView.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        TextView titleTextView = (TextView) findViewById(R.id.title);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Bundle extras = getIntent().getExtras();
        String fileName = extras.getString("fileName");
        if (fileName != null) {
            String path = getFilesDir() + getString(R.string.path_separator) + fileName;
            Bitmap bmp = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bmp);
            titleTextView.setText(fileName);
        }
    }
}