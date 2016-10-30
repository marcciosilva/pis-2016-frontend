package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                String filePath = item.getTitle();
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri
                        .parse("content://com.sonda.emsysmobile/" + filePath), "image/*");
                startActivity(intent);

            }
        });
    }

    private ArrayList<ImageItem> getData() {
        Bundle extras = getIntent().getExtras();
        ArrayList<ImageDescriptionDto> imageDescriptions =
                (ArrayList<ImageDescriptionDto>) extras.getSerializable("imageDescriptions");
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        for (int i = 0; i < imageDescriptions.size(); i++) {
            ImageDescriptionDto description = imageDescriptions.get(i);
            String path =
                    getFilesDir() + getString(R.string.path_separator) +
                            description.getFileName();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Date creationDate = description.getDeliveryDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            imageItems.add(new ImageItem(bitmap, description.getFileName(), description
                    .getUser(), dateFormat.format(creationDate)));
        }
        return imageItems;
    }

}
