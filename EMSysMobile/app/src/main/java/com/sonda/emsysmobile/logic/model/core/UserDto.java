package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marccio on 04-Nov-16.
 */

public class UserDto implements Serializable {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("roles")
    RoleDto roles;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDto getRoles() {
        return roles;
    }

    public void setRoles(RoleDto roles) {
        this.roles = roles;
    }

    public boolean isZoneDispatcher() {
        if (roles != null) {
            List<ZoneDto> zones = roles.getZones();
            return ((zones != null) && (zones.size() != 0));
        } else {
            return false;
        }
    }

    public boolean isResource() {
        if (roles != null) {
            List<ResourceDto> resources = roles.getResources();
            return ((resources != null) && (resources.size() != 0));
        } else {
            return false;
        }
    }
}
