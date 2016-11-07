package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

/**
 * Created by ssainz on 10/25/16.
 */
public class OfflineUpdateDescriptionRequest<T> extends AbstractRequest<T> {

    public static final String OFFLINE_ATTACH_DESC_PATH =
            "/eventos/actualizardescripcionrecursooffline";
    private static final String TAG = OfflineUpdateDescriptionRequest.class.getName();
    private OfflineAttachDescriptionDto offlineAttachDescriptionDto;

    public OfflineUpdateDescriptionRequest(Context context, Type responseType,
                                           OfflineAttachDescriptionDto
                                                   offlineAttachDescriptionDto) {
        super(context, responseType, RequestType.POST);
        this.offlineAttachDescriptionDto = offlineAttachDescriptionDto;
    }

    @Override
    protected final String getPath() {
        return OFFLINE_ATTACH_DESC_PATH;
    }

    @Override
    protected final JsonObject getBody() {
        // Se convierten los parametros del request para el servicio externo en un objeto de json.
        String json = new Gson().toJson(offlineAttachDescriptionDto);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        jsonObject.addProperty("time_stamp", simpleDateFormat
                .format(offlineAttachDescriptionDto.getTimeStamp()));
        // Se imprime string en json.
        Log.d(TAG, "Request body:");
        Log.d(TAG, jsonObject.toString());
        return jsonObject;
    }

}


