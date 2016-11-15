package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.views.adapters.GridViewAdapter;

import java.util.ArrayList;

/**
 * Created by marccio on 13/10/16.
 */

public class ImageGalleryView extends AppCompatActivity {

    private static final String TAG = ImageGalleryView.class.getName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        // Se agrega la flecha de ir hacia atras.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        GridView gridView = (GridView) findViewById(R.id.gridView);
        Bundle extras = getIntent().getExtras();
        ArrayList<ImageDescriptionDto> imageDescriptions =
                (ArrayList<ImageDescriptionDto>) extras.getSerializable("imageDescriptions");
        GridViewAdapter gridAdapter =
                new GridViewAdapter(this, R.layout.grid_item_layout, imageDescriptions);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageDescriptionDto item =
                        (ImageDescriptionDto) parent.getItemAtPosition(position);
                Intent intent = new Intent(ImageGalleryView.this, ImageDetailView.class);
                intent.putExtra("imageId", item.getId());
                startActivity(intent);
            }
        });
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
