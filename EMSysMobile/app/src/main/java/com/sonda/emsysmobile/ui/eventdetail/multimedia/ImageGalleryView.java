package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.views.adapters.GridViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        gridView = (GridView) findViewById(R.id.gridView);
        Bundle extras = getIntent().getExtras();
        ArrayList<ImageDescriptionDto> imageDescriptions =
                (ArrayList<ImageDescriptionDto>) extras.getSerializable("imageDescriptions");
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, imageDescriptions);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageDescriptionDto item = (ImageDescriptionDto) parent.getItemAtPosition(position);
                Intent intent = new Intent(ImageGalleryView.this, ImageTest.class);
                intent.putExtra("imageUrl", item.getImageUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_back) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu_only_back, menu);
        return true;
    }

}
