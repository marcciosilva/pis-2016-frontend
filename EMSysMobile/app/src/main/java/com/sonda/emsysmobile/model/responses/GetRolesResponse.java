package com.sonda.emsysmobile.model.responses;

import com.google.gson.annotations.SerializedName;
import com.sonda.emsysmobile.model.core.RoleDto;

/**
 * Created by marccio on 9/28/16.
 */

public class GetRolesResponse extends EmsysResponse {

    @SerializedName("response")
    private RoleDto roles;

    public final RoleDto getRoles() {
        return roles;
    }

    public final void setRoles(RoleDto roles) {
        this.roles = roles;
    }

    @Override
    public final boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!GetRolesResponse.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final GetRolesResponse other = (GetRolesResponse) obj;
        return ((super.equals(obj)) && (roles.equals(other.roles)));
    }

}
