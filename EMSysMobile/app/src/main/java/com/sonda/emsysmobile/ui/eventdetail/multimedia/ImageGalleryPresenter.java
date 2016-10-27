package com.sonda.emsysmobile.ui.eventdetail.multimedia;

import android.content.Context;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;
import com.sonda.emsysmobile.logic.model.core.attachments.ImageDescriptionDto;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.List;

/**
 * Created by marccio on 27-Oct-16.
 */
public class ImageGalleryPresenter {

    public static void loadImages(final Context context, List<ImageDescriptionDto>
            imageDescriptions) {
        if (imageDescriptions.isEmpty()) {
            // No hay imagenes asociadas al evento.
            UIUtils.handleErrorMessage(context, ErrorCodeCategory.NO_AVAILABLE_MULTIMEDIA
                    .getNumVal(), context
                    .getString(R.string.error_no_available_multimedia));
        } else {

            }
        }

//        request.setAttributes(imageDescriptions);


//        request.setAttributes(eventId);
//        request.setListener(new Response.Listener<EventDetailsResponse>() {
//            @Override
//            public void onResponse(EventDetailsResponse response) {
//                int responseCode = response.getCode();
//                if (responseCode == ErrorCodeCategory.SUCCESS.getNumVal()) {
//                    EventDto event = response.getEvent();
//                    if (event != null) {
//                        for (ExtensionDto extension : event.getExtensions()) {
//                            extension.setEvent(event);
//                        }
//                    }
//                    callback.onSuccess(event);
//                } else {
//                    //TODO soportar mensaje de error en EventsResponse
//                    //callback.onError(response.getInnerResponse().getMsg(), responseCode);
//                    callback.onLogicError("Unsupported", 1);
//                }
//            }
//        });
//        request.setErrorListener(new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onNetworkError(error);
//            }
//        });
//        request.execute();

}
