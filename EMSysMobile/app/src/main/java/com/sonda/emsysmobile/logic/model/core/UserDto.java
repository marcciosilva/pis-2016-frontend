package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marccio on 04-Nov-16.
 */

public class UserDto implements Serializable {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("roles")
    private RoleDto roles;

    public UserDto() {
    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final RoleDto getRoles() {
        return roles;
    }

    public final void setRoles(RoleDto roles) {
        this.roles = roles;
    }

    public final boolean isZoneDispatcher() {
        if (roles != null) {
            List<ZoneDto> zones = roles.getZones();
            return ((zones != null) && (zones.size() != 0));
        } else {
            return false;
        }
    }

    public final boolean isResource() {
        if (roles != null) {
            List<ResourceDto> resources = roles.getResources();
            return ((resources != null) && (resources.size() != 0));
        } else {
            return false;
        }
    }
}
