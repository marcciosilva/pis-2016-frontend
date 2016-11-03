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

/**
 * Created by marccio on 28-Oct-16.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();
    private static final String TAG = GridViewAdapter.class.getName();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) convertView.findViewById(R.id.text);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.creator = (TextView) convertView.findViewById(R.id.creator);
            holder.creationDate = (TextView) convertView.findViewById(R.id.creationDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageDescriptionDto item = (ImageDescriptionDto) data.get(position);
        holder.imageTitle.setText("Imagen " + Integer.toString(item.getId()));
//        ImageLoader imageLoader = AppRequestQueue.getInstance(context).getImageLoader();

        if ((convertView.findViewById(R.id.progress_bar).getVisibility() == View.INVISIBLE)
                && (holder.image.getVisibility() == View.VISIBLE)) {
            convertView.findViewById(R.id.progress_bar)
                    .setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.INVISIBLE);
        }
        ImageGalleryPresenter.loadThumbnail(convertView, context, item, holder);




        holder.creator.setText("Subido por: " + item.getUser());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.creationDate.setText(dateFormat.format(item.getDeliveryDate()));
        return convertView;
    }

    public static class ViewHolder {
        public TextView imageTitle;
        public ImageView image;
        public TextView creator;
        public TextView creationDate;
    }
}