package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.interfaces.ProgressBarListener;
import com.sonda.emsysmobile.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 27-Oct-16.
 */
public class ImageGalleryPresenter {

    private static final String TAG = ImageGalleryPresenter.class.getName();

    public static void loadImages(final Context context, final List<ImageDescriptionDto>
            imageDescriptions) {
        final ProgressBarListener progressBarListener = (ProgressBarListener) context;
        progressBarListener.showProgressBar();
        if (imageDescriptions.isEmpty()) {
            // No hay imagenes asociadas al evento.
            progressBarListener.hideProgressBar();
            UIUtils.handleErrorMessage(context, ErrorCodeCategory.NO_AVAILABLE_MULTIMEDIA
                    .getNumVal(), context
                    .getString(R.string.error_no_available_multimedia));
        } else {
            Intent intent = new Intent(context, ImageGalleryView.class);
            intent.putExtra("imageDescriptions",
                    (ArrayList<ImageDescriptionDto>) imageDescriptions);
            progressBarListener.hideProgressBar();
            context.startActivity(intent);
//            MultimediaManager multimediaManager = MultimediaManager.getInstance(context);
//            multimediaManager.setImageDescriptions(imageDescriptions);
//            multimediaManager.fetchImages(new ApiCallback<List<ImageDataDto>>() {
//                @Override
//                public void onSuccess(List<ImageDataDto> imageDataList) {
//                    progressBarListener.hideProgressBar();
//                    initImageGalleryView(context, imageDataList, imageDescriptions);
//                }
//
//                @Override
//                public void onLogicError(String errorMessage, int errorCode) {
//                    progressBarListener.hideProgressBar();
//                    UIUtils.handleErrorMessage(context, errorCode, errorMessage);
//                }
//
//                @Override
//                public void onNetworkError(VolleyError error) {
//                    progressBarListener.hideProgressBar();
//                    handleVolleyErrorResponse(context, error, new DialogInterface.OnClickListener
//                            () {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            loadImages(context, imageDescriptions);
//                        }
//                    });
//                }
//            });
        }
    }

}
