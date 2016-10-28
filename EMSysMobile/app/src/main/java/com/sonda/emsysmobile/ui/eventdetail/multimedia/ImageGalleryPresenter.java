package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDataDto;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.ui.activities.SplashActivity;
import com.sonda.emsysmobile.ui.changeview.CustomMarkerData;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailMapPresenter;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsView;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 27-Oct-16.
 */
public class ImageGalleryPresenter {

    public static void loadImages(final Context context, final List<ImageDescriptionDto>
            imageDescriptions) {
        if (imageDescriptions.isEmpty()) {
            // No hay imagenes asociadas al evento.
            UIUtils.handleErrorMessage(context, ErrorCodeCategory.NO_AVAILABLE_MULTIMEDIA
                    .getNumVal(), context
                    .getString(R.string.error_no_available_multimedia));
        } else {
            MultimediaManager multimediaManager = MultimediaManager.getInstance(context);
            multimediaManager.setImageDescriptions(imageDescriptions);
            multimediaManager.fetchImages(new ApiCallback<List<ImageDataDto>>() {
                @Override
                public void onSuccess(List<ImageDataDto> imageDataList) {
                    initImageGalleryView(context, imageDataList);
                }

                @Override
                public void onLogicError(String errorMessage, int errorCode) {
                    UIUtils.handleErrorMessage(context, errorCode, errorMessage);
                }

                @Override
                public void onNetworkError(VolleyError error) {
                    handleVolleyErrorResponse(context, error, new DialogInterface.OnClickListener
                            () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadImages(context, imageDescriptions);
                        }
                    });
                }
            });
        }
    }

    private static void initImageGalleryView(Context context, List<ImageDataDto> imageDataList) {
        Intent intent = new Intent(context, ImageGalleryView.class);
        ArrayList<ImageDataDto> arrayLst = (ArrayList<ImageDataDto>) imageDataList;
        intent.putExtra("imageDataList", arrayLst);
        context.startActivity(intent);
    }

}
