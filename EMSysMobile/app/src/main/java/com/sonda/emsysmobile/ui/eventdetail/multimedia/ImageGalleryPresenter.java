package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.android.volley.VolleyError;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
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
import com.sonda.emsysmobile.utils.MultimediaUtils;
import com.sonda.emsysmobile.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 27-Oct-16.
 */
public class ImageGalleryPresenter {

    private static final String TAG = ImageGalleryPresenter.class.getName();

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
        ArrayList<String> fileNames = new ArrayList<>();
        Intent intent = new Intent(context, ImageGalleryView.class);
        for (int i = 0; i < imageDataList.size(); i++) {
            try {
                byte[] imageAsBytes = Base64.decode(imageDataList.get(i).getData(), 0);
                // Archivo a guardar.
//                    File file = new File(path, imageDataList.get(i).getName());
                File file = new File(context.getCacheDir(), imageDataList.get(i).getName());
                Log.d(TAG, file.getAbsolutePath());
                OutputStream fOut = new FileOutputStream(file);
                Bitmap pictureBitmap =
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                // TODO manejar compresion de acuerdo a tipo de imagen.
                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();
                MediaStore.Images.Media
                        .insertImage(context.getContentResolver(), file.getAbsolutePath(), file
                                .getName(), file.getName());
                // Agrego el nombre del archivo para pasar en el intent.
                fileNames.add(imageDataList.get(i).getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra("fileNames", fileNames);
        context.startActivity(intent);
    }

}
