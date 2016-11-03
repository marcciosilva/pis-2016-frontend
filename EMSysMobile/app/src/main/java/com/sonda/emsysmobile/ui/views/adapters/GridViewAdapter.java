package com.sonda.emsysmobile.ui.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.AppRequestQueue;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.ImageItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        final View finalViewReference;
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
        finalViewReference = convertView;
        ImageDescriptionDto item = (ImageDescriptionDto) data.get(position);
        holder.imageTitle.setText("Imagen " + Integer.toString(item.getId()));
        ImageLoader imageLoader = AppRequestQueue.getInstance(context).getImageLoader();
        // TODO descomentar esto al probar con el backend
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String imageUrl = sharedPrefs.getString("backendUrl", BuildConfig.BASE_URL) +
//                "/eventos/getimagedata?idImagen="
//                + Integer.toString(item.getId());
//        holder.image.setImageUrl(imageUrl, imageLoader);
        String imageUrl =
                "http://zeroturnaround.com/wp-content/uploads/2016/02/Android-Picasso-Images-Loading.png";
        item.setImageUrl(imageUrl);
        Picasso.with(context)
                .load(imageUrl)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        finalViewReference.findViewById(R.id.progressBar).setVisibility(View.GONE);
                        holder.image.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        finalViewReference.findViewById(R.id.error_layout)
                                .setVisibility(View.VISIBLE);
                        finalViewReference.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }
                });
        holder.creator.setText("Subido por: " + item.getUser());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.creationDate.setText(dateFormat.format(item.getDeliveryDate()));
        return convertView;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
        TextView creator;
        TextView creationDate;
    }
}