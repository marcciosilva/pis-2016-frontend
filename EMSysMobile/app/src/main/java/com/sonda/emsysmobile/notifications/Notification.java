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
    private String title;
    private String description;
    private String code;
    private Date date;
    private int eventId;
    private int extensionId;
    private String zoneName;
    private boolean isRead;

    public Notification(String title, String description, String code, int eventId, int extensionId,
                        String zoneName) {
        this.title = title;
        this.description = description;
        this.code = code;
        this.eventId = eventId;
        this.extensionId = extensionId;
        this.zoneName = zoneName;
        this.isRead = false;
    }

    public Notification(String code, int objectId, int extensionId, String zoneName) {
        this.code = code;
        this.eventId = objectId;
        this.date = new Date();
        this.extensionId = extensionId;
        this.zoneName = zoneName;
        this.isRead = false;
        buildTitleAndDescription();
    }

    private void buildTitleAndDescription() {
        final String connectorString = " en ";
        if (code.equals("AE")) {
            this.title = "Se ha dado de alta un nuevo evento";
            this.description =
                    "Se ha creado el evento " + this.eventId + connectorString + this.zoneName;
        }
        if (code.equals("ME") || code.equals("AA") || code.equals("AI")
                || code.equals("AV") || code.equals("AG")) {
            this.title = "Se ha modificado un evento";
            switch (code) {
                case "AA":
                    this.description =
                            "Se adjuntó un audio al evento " + this.eventId + connectorString +
                                    this.zoneName;
                    break;
                case "AI":
                    this.description =
                            "Se adjuntó una imagen al evento " + this.eventId + connectorString +
                                    this.zoneName;
                    break;
                case "AV":
                    this.description =
                            "Se adjuntó un video al evento " + this.eventId + connectorString +
                                    this.zoneName;
                    break;
                case "AG":
                    this.description =
                            "Se adjuntó una ubicación al evento " + this.eventId + connectorString +
                                    this.zoneName;
                    break;
                default:
                    this.description =
                            "Se modificó el evento " + this.eventId + connectorString +
                                    this.zoneName;
                    break;
            }
        }
        if (code.equals("CE")) {
            this.title = "Se ha cerrado un evento";
            this.description =
                    "Se cerró el evento " + this.eventId + connectorString + this.zoneName;
        }
        if (code.equals("SE")) {
            this.title = "Se ha asignado un evento";
            this.description =
                    "El recurso ha sido asignado al evento " + this.eventId + connectorString +
                            this.zoneName;
        }
        if (code.equals("RE")) {
            this.title = "El recurso ya no está asignado a un evento";
            this.description =
                    "El recurso se ha retirado del evento " + this.eventId + connectorString +
                            this.zoneName;
        }
    }

    protected Notification(Parcel in) {
        title = in.readString();
        description = in.readString();
        code = in.readString();
        eventId = in.readInt();
        extensionId = in.readInt();
        zoneName = in.readString();
        isRead = in.readByte() != 0;
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

    public final int getEventId() {
        return eventId;
    }

    public final void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final int getExtensionId() {
        return extensionId;
    }

    public final void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public final String getZoneName() {
        return zoneName;
    }

    public final void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public final boolean isRead() {
        return isRead;
    }

    public final void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public final String toString() {
        return "Notification received. \n Notification Code: " + this.code + "\n Primary key: " +
                this.eventId;
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
        parcel.writeInt(eventId);
        parcel.writeInt(extensionId);
        parcel.writeString(zoneName);
        parcel.writeByte((byte) (isRead ? 1 : 0));
    }

    public final NotificationsEvents getEventFromCode() {
        if (code.equals("AE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        }
        if (code.equals("ME") || code.equals("AA") || code.equals("AI")
                || code.equals("AV") || code.equals("AG")) {
            return NotificationsEvents.UPDATE_ONE_EVENT;
        }
        if (code.equals("CE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        }
        if (code.equals("SE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        }
        if (code.equals("RE")) {
            return NotificationsEvents.UPDATE_EVENTS_LIST;
        }
        return NotificationsEvents.NONE;
    }
}
