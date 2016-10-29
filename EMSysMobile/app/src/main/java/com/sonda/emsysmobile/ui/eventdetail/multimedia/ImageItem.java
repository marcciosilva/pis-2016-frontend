package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.graphics.Bitmap;

/**
 * Created by marccio on 28-Oct-16.
 */

public class ImageItem {
    private Bitmap image;
    private String title;

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public final Bitmap getImage() {
        return image;
    }

    public final void setImage(Bitmap image) {
        this.image = image;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }
}
