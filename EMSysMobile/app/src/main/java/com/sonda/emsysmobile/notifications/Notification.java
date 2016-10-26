package com.sonda.emsysmobile.notifications;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ssainz on 10/13/16.
 */
public class Notification implements Parcelable {

    public static final String DEFAULT_TITLE = "EMSYS Mobile";
    public static final String DEFAULT_DESCRIPTION = "Nueva notificación";

    private String title;
    private String description;
    private String code;
    private int objectId;

    public Notification(String title, String description, String code, int objectId) {
        this.title = title;
        this.description = description;
        this.code = code;
        this.objectId = objectId;
    }

    public Notification(String code, int objectId) {
        this.title = DEFAULT_TITLE;
        this.description = DEFAULT_DESCRIPTION;
        this.code = code;
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    protected Notification(Parcel in) {
        title = in.readString();
        description = in.readString();
        code = in.readString();
        objectId = in.readInt();
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    @Override
    public String toString() {
        return "Notification received. \n Notification Code: " + this.code + "\n Primary key: " + this.objectId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(code);
        parcel.writeInt(objectId);
    }

    public NotificationsEvents getEventFromCode() {
        if (code.equals("AE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        } if (code.equals("ME")) {
            return NotificationsEvents.UPDATE_ONE_EVENT;
        } if (code.equals("CE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        } if (code.equals("SE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        } if (code.equals("RE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        }
        return NotificationsEvents.NONE;
    }
}
