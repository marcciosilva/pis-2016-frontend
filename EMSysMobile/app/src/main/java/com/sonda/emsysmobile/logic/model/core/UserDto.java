package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by marccio on 04-Nov-16.
 */

public class UserDto implements Serializable {

    @SerializedName("user")
    String username;

    @SerializedName("pass")
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
}
