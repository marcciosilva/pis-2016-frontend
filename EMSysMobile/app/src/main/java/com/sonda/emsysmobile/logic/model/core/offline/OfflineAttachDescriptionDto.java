package com.sonda.emsysmobile.logic.model.core.offline;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.UserDto;

import java.util.Date;

/**
 * Created by jmsmuy on 04/11/16.
 */

public class OfflineAttachDescriptionDto implements OfflineDto {

    @SerializedName("descripcion")
    private String description;

    @SerializedName("id_extension")
    private int extensionId;

    @SerializedName("user_data")
    private UserDto userData;

    @SerializedName("time_stamp")
    private Date timeStamp;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public void setUserData(UserDto userData) {
        this.userData = userData;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
