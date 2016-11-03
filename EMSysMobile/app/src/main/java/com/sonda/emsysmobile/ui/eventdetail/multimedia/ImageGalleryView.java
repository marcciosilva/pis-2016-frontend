package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.views.adapters.GridViewAdapter;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marccio on 13/10/16.
 */

public class ImageGalleryView extends AppCompatActivity {

    private static final String TAG = ImageGalleryView.class.getName();
    private List<ImageDataDto> mImageDataList;
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        // Se agrega la flecha de ir hacia atras.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        gridView = (GridView) findViewById(R.id.gridView);
        Bundle extras = getIntent().getExtras();
        ArrayList<ImageDescriptionDto> imageDescriptions =
                (ArrayList<ImageDescriptionDto>) extras.getSerializable("imageDescriptions");
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, imageDescriptions);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (v.findViewById(R.id.error_layout).getVisibility() == View.VISIBLE) {
                    UIUtils.handleErrorMessage(ImageGalleryView.this, ErrorCodeCategory
                            .NO_AVAILABLE_MULTIMEDIA
                            .getNumVal(), "La imágen no se puede visualizar.");
                } else {
                    ImageDescriptionDto item =
                            (ImageDescriptionDto) parent.getItemAtPosition(position);
                    Intent intent = new Intent(ImageGalleryView.this, ImageDetailView.class);
                    intent.putExtra("imageUrl", item.getImageUrl());
                    startActivity(intent);
                }
            }
        });
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
