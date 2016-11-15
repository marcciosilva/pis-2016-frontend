package com.sonda.emsysmobile.ui.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.ImageGalleryPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marccio on 28-Oct-16.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private List data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, List data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View localConvertView = convertView;
        if (localConvertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            localConvertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.setImageTitle((TextView) localConvertView.findViewById(R.id.text));
            holder.setImage((ImageView) localConvertView.findViewById(R.id.image));
            holder.setCreator((TextView) localConvertView.findViewById(R.id.creator));
            holder.setCreationDate((TextView) localConvertView.findViewById(R.id.creationDate));
            localConvertView.setTag(holder);
        } else {
            holder = (ViewHolder) localConvertView.getTag();
        }
        ImageDescriptionDto imageDescription = (ImageDescriptionDto) data.get(position);
        holder.getImageTitle().setText("Imagen " + Integer.toString(imageDescription.getId()));
        if ((localConvertView.findViewById(R.id.progress_bar).getVisibility() == View.INVISIBLE)
                && (holder.getImage().getVisibility() == View.VISIBLE)) {
            localConvertView.findViewById(R.id.progress_bar)
                    .setVisibility(View.VISIBLE);
            holder.getImage().setVisibility(View.INVISIBLE);
        }
        ImageGalleryPresenter.loadThumbnail(localConvertView, context, imageDescription, holder);
        holder.getCreator().setText("Subido por: " + imageDescription.getUser());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.getCreationDate().setText(dateFormat.format(imageDescription.getDeliveryDate()));
        return localConvertView;
    }

    public static class ViewHolder {
        public TextView imageTitle;
        public ImageView image;
        public TextView creator;
        public TextView creationDate;

        public TextView getImageTitle() {
            return imageTitle;
        }

        public void setImageTitle(TextView imageTitle) {
            this.imageTitle = imageTitle;
        }

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public TextView getCreator() {
            return creator;
        }

        public void setCreator(TextView creator) {
            this.creator = creator;
        }

        public TextView getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(TextView creationDate) {
            this.creationDate = creationDate;
        }
    }
}