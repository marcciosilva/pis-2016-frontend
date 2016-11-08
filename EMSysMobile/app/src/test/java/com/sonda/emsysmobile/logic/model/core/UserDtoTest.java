package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 08-Nov-16.
 */
public class UserDtoTest {

    private UserDto mUserDto;
    private String mUsername;
    private String mPassword;
    private RoleDto mRoles;

    @Before
    public void setUp() throws Exception {
        mUserDto = new UserDto();
        mUsername = "username";
        mUserDto.setUsername(mUsername);
        mPassword = "password";
        mUserDto.setPassword(mPassword);
        mRoles = new RoleDto();
        mUserDto.setRoles(mRoles);
    }

    @Test
    public void getUsername() throws Exception {
        assertTrue(mUserDto.getUsername().equals(mUsername));
    }

    @Test
    public void setUsername() throws Exception {
        String testUsername = "test_username";
        mUserDto.setUsername(testUsername);
        assertTrue(mUserDto.getUsername().equals(testUsername));
    }

    @Test
    public void getPassword() throws Exception {
        assertTrue(mUserDto.getPassword().equals(mPassword));
    }

    @Test
    public void setPassword() throws Exception {
        String testUsername = "test_username";
        mUserDto.setUsername(testUsername);
        assertTrue(mUserDto.getUsername().equals(testUsername));
    }

    @Test
    public void getRoles() throws Exception {
        assertTrue(mUserDto.getRoles().equals(mRoles));
    }

    @Test
    public void setRoles() throws Exception {
        mUserDto.setRoles(null);
        assertTrue(mUserDto.getRoles() == null);
    }

}