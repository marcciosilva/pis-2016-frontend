package com.sonda.emsysmobile.logic.model.core.offline;

import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.UserDto;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by marccio on 08-Nov-16.
 */
public class OfflineAttachDescriptionDtoTest {

    private OfflineAttachDescriptionDto offlineAttachDescriptionDto;
    private String mDescription;
    private int mExtensionId;
    private UserDto mUserData;
    private Date mTimeStamp;

    @Before
    public void setUp() throws Exception {
        offlineAttachDescriptionDto = new OfflineAttachDescriptionDto();
        mDescription = "description";
        offlineAttachDescriptionDto.setDescription(mDescription);
        mExtensionId = 1;
        offlineAttachDescriptionDto.setExtensionId(mExtensionId);
        mUserData = new UserDto();
        mUserData.setRoles(new RoleDto());
        mUserData.setUsername("username");
        mUserData.setPassword("password");
        offlineAttachDescriptionDto.setUserData(mUserData);
        mTimeStamp = new Date(1992,1,1);
        offlineAttachDescriptionDto.setTimeStamp(mTimeStamp);
    }

    @Test
    public void setDescription() throws Exception {
        final String altDescription = "alt_description";
        offlineAttachDescriptionDto.setDescription(altDescription);
        assertTrue(offlineAttachDescriptionDto.getDescription().equals(altDescription));
    }

    @Test
    public void setExtensionId() throws Exception {
        final int altExtensionId = 2;
        offlineAttachDescriptionDto.setExtensionId(altExtensionId);
        assertTrue(offlineAttachDescriptionDto.getExtensionId() == altExtensionId);
    }

    @Test
    public void setUserData() throws Exception {
        UserDto testUerData = new UserDto();
        offlineAttachDescriptionDto.setUserData(testUerData);
        assertFalse(offlineAttachDescriptionDto.getUserData().equals(mUserData));
    }

    @Test
    public void getTimeStamp() throws Exception {

    }

    @Test
    public void setTimeStamp() throws Exception {
        assertFalse(offlineAttachDescriptionDto.getTimeStamp().equals(new Date()));
    }

}