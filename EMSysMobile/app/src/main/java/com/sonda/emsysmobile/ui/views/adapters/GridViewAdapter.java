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
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) convertView.findViewById(R.id.text);
            holder.image = (NetworkImageView) convertView.findViewById(R.id.image);
            holder.creator = (TextView) convertView.findViewById(R.id.creator);
            holder.creationDate = (TextView) convertView.findViewById(R.id.creationDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageDescriptionDto item = (ImageDescriptionDto) data.get(position);
        holder.imageTitle.setText("Imagen " + Integer.toString(item.getId()));
        ImageLoader imageLoader = AppRequestQueue.getInstance(context).getImageLoader();
        // TODO descomentar esto al probar con el backend
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String imageUrl = sharedPrefs.getString("backendUrl", BuildConfig.BASE_URL) +
//                "/eventos/getimagedata?idImagen="
//                + Integer.toString(item.getId());
//        holder.image.setImageUrl(imageUrl, imageLoader);
        String imageUrl = "https://i.ytimg.com/vi/m5d1FlSeF-M/maxresdefault.jpg";
        item.setImageUrl(imageUrl);
        holder.image.setImageUrl(imageUrl, imageLoader);
        holder.creator.setText("Subido por: " + item.getUser());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.creationDate.setText(dateFormat.format(item.getDeliveryDate()));
        return convertView;
    }

    static class ViewHolder {
        TextView imageTitle;
        NetworkImageView image;
        TextView creator;
        TextView creationDate;
    }
}