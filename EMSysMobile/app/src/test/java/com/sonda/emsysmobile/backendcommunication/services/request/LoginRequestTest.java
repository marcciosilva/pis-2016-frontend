package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.GetRolesResponse;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class LoginRequestTest extends BaseMockTest {

    private LoginRequest<Integer> mLoginRequest;
    private RoleDto mRoleDto;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ZoneDto mZone1 = new ZoneDto("zona 1", 1234, "ue1");
        ZoneDto mZone2 = new ZoneDto("zona 2", 8262, "ue2");
        ArrayList<ZoneDto> mZones = new ArrayList<>();
        mZones.add(mZone1);
        mZones.add(mZone2);
        ResourceDto mResource1 = new ResourceDto("A123", 1);
        ResourceDto mResource2 = new ResourceDto("B621", 2);
        ArrayList<ResourceDto> mResources = new ArrayList<>();
        mResources.add(mResource1);
        mResources.add(mResource2);
        mRoleDto = new RoleDto(mZones, mResources);
        mLoginRequest = new LoginRequest<>(context, EmsysResponse.class, mRoleDto);
    }

    @Test
    public void getPath() throws Exception {
        assertTrue(mLoginRequest.getPath().equals(LoginRequest.LOGIN_PATH));
    }

    @Test
    public void getBody() throws Exception {
        // TODO mejorar.
        mLoginRequest.getBody();
    }

}