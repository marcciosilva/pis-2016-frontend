package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.graphics.Bitmap;

/**
 * Created by marccio on 28-Oct-16.
 */

public class ImageItem {
    private Bitmap image;
    private String title;
    private String creator;
    private String creationDate;

    public ImageItem(Bitmap image, String title, String creator, String creationDate) {
        this.image = image;
        this.title = title;
        this.creator = creator;
        this.creationDate = creationDate;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
