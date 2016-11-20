package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.interfaces.ProgressBarListener;
import com.sonda.emsysmobile.ui.views.adapters.GridViewAdapter;
import com.sonda.emsysmobile.utils.UIUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marccio on 27-Oct-16.
 */
public final class ImageGalleryPresenter {

    private ImageGalleryPresenter() {
        // Debe ser privado porque no debe ser utilizado.
    }

    public static void loadGallery(final Context context, final List<ImageDescriptionDto>
            imageDescriptions) {
        final ProgressBarListener progressBarListener = (ProgressBarListener) context;
        progressBarListener.showProgressBar();
        if (imageDescriptions.isEmpty()) {
            // No hay imagenes asociadas al evento.
            progressBarListener.hideProgressBar();
            if (((context instanceof Activity) && (!((Activity) context).isFinishing()))) {
                UIUtils.handleErrorMessage(context, ErrorCodeCategory.NO_AVAILABLE_MULTIMEDIA
                        .getNumVal(), context
                        .getString(R.string.error_no_available_multimedia));
            }
        } else {
            Intent intent = new Intent(context, ImageGalleryView.class);
            intent.putExtra("imageDescriptions",
                    (ArrayList<ImageDescriptionDto>) imageDescriptions);
            progressBarListener.hideProgressBar();
            context.startActivity(intent);
        }
    }

    public static void loadThumbnail(final View view, Context context, ImageDescriptionDto item,
                                     final GridViewAdapter.ViewHolder holder) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String imageUrl = sharedPrefs.getString("backendUrl", BuildConfig.BASE_URL) +
                "/adjuntos/getimagethumbnail?idImagen="
                + Integer.toString(item.getId());
        item.setImageUrl(imageUrl);

        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        view.findViewById(R.id.progress_bar)
                                .setVisibility(View.INVISIBLE);
                        holder.image.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        view.findViewById(R.id.error_layout)
                                .setVisibility(View.VISIBLE);
                        view.findViewById(R.id.relative_container)
                                .setVisibility(View.GONE);
                    }

                });
    }
}
