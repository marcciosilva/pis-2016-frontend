package com.sonda.emsysmobile.logic.model.core.offline;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.logic.model.core.UserDto;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public UserDto getUserData() {
        return userData;
    }

    public void setUserData(UserDto userData) {
        this.userData = userData;
    }
}
