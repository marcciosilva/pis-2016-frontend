package com.sonda.emsysmobile.backendcommunication.services.request;

        import android.content.Context;
        import android.util.Log;

        import com.google.gson.JsonObject;

        import java.lang.reflect.Type;

/**
 * Created by mserralta on 17/10/16.
 */

public class EventDetailsRequest<T> extends AbstractRequest<T> {

    private static final String TAG = EventDetailsRequest.class.getName();
    public static final String EVENT_DETAILS_PATH = "/users/authenticate";

    private String eventId;


    public EventDetailsRequest(Context context, Type responseType) {
        super(context, responseType, RequestType.POST);
    }

    @Override
    protected String getPath() {
        return EVENT_DETAILS_PATH;
    }

    @Override
    protected JsonObject getBody() {
        JsonObject body = new JsonObject();
        body.addProperty("idEvento", this.eventId);

        Log.d(TAG, "Request body:");
        Log.d(TAG, body.toString());

        return body;
    }

    public final void setAttributes(String eventId) {
        this.eventId = eventId;
    }
}