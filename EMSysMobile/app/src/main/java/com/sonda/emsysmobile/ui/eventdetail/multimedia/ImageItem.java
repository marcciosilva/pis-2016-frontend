package com.sonda.emsysmobile.ui.eventdetail.multimedia;

/**
 * Created by marccio on 28-Oct-16.
 */

public class ImageItem {
    private String imageUrl;
    private String title;
    private String creator;
    private String creationDate;

    public ImageItem(String imageUrl, String title, String creator, String creationDate) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.creator = creator;
        this.creationDate = creationDate;
    }

    public final String getImageUrl() {
        return imageUrl;
    }

    public final void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
