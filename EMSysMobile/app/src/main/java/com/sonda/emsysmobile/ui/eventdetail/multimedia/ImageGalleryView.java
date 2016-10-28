package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
//        mImageDataList = (ArrayList<ImageDataDto>) extras.getSerializable("imageDataList");
//        for (ImageDataDto imageData : mImageDataList) {
//            Log.d(TAG, imageData.getData().toString());
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
