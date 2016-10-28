package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mserralta on 13/10/16.
 */

public class ImageGalleryView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ImageGalleryView.class.getName();
    private List<ImageDataDto> mImageDataList;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        Bundle extras = getIntent().getExtras();
        int i = 0;
        ArrayList<String> fileNames = extras.getStringArrayList("fileNames");
        if (fileNames != null) {
            ImageView image = (ImageView) findViewById(R.id.imageView1);
            String path = Environment.getExternalStorageDirectory() + "/" + fileNames.get(0);
            Bitmap bmp = BitmapFactory.decodeFile(path);
            image.setImageBitmap(bmp);
        }
//        while (extras.getByteArray("image" + Integer.toString(i)) != null) {
//            byte[] byteArray = extras.getByteArray(("image" + Integer.toString(i)));
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            ImageView image = (ImageView) findViewById(R.id.imageView1);
//            image.setImageBitmap(bmp);
//            i++;
//        }
    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.button_images) {
//            Log.d(TAG, "Botón de imágenes pulsado");
//            Log.d(TAG, "Cantidad de descripciones de imagenes para el evento: " +
//                    Integer.toString(mEvent.getImageDescriptions().size()));
//            ImageGalleryPresenter.loadImages(ImageGalleryView.this, mEvent.getImageDescriptions
// ());
//        }
    }
}
