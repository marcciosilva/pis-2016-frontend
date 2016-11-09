package com.sonda.emsysmobile.notifications;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ssainz on 10/13/16.
 */
public class Notification implements Parcelable {

    public static final String DEFAULT_TITLE = "EMSYS Mobile";
    public static final String DEFAULT_DESCRIPTION = "Nueva notificación";

    private String title;
    private String description;
    private String code;
    private Date date;
    private int objectId;
    private boolean isRead;

    public Notification(String title, String description, String code, int objectId) {
        this.title = title;
        this.description = description;
        this.code = code;
        this.objectId = objectId;
        this.isRead = false;
    }

    public Notification(String code, int objectId) {
        this.code = code;
        this.objectId = objectId;
        this.date = new Date();
        buildTitleAndDescription();
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final int getObjectId() {
        return objectId;
    }

    public final void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
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
    public final String toString() {
        return "Notification received. \n Notification Code: " + this.code + "\n Primary key: " + this.objectId;
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(code);
        parcel.writeInt(objectId);
    }

    public final NotificationsEvents getEventFromCode() {
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

    private void buildTitleAndDescription() {
        if (code.equals("AE")) {
            this.title = "Se ha dado de alta un nuevo evento";
            this.description = "El evento " + this.objectId + " se ha dado de alta para alguna de tus zonas";
        } if (code.equals("ME")) {
            this.title = "Se ha modificado un evento";
            this.description = "El evento " + this.objectId + " ha sido modificado";
        } if (code.equals("CE")) {
            this.title = "Se ha cerrado una extensión";
            this.description = "La extensión del evento " + this.objectId + " ha sido cerrada";
        } if (code.equals("SE")) {
            this.title = "Se asignó una extensión";
            this.description = "La extensión perteneciente al evento " + this.objectId + " ha sido asignada";
        } if (code.equals("RE")) {
            this.title = "Ya no estás asignado a un evento";
            this.description = "Ya no estás asignado al evento " + this.objectId;
        }
    }
}
