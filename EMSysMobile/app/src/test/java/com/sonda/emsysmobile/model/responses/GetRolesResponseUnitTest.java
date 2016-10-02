package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.core.ResourceDto;
import com.sonda.emsysmobile.model.core.RoleDto;
import com.sonda.emsysmobile.model.core.ZoneDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class GetRolesResponseUnitTest {

    private GetRolesResponse mGetRolesResponse;
    private RoleDto mRoleDto;

    @Before
    public void executeBeforeEach() {
        mGetRolesResponse = new GetRolesResponse();
        mGetRolesResponse.code = 0;
        ZoneDto mZona1 = new ZoneDto("zona 1", "1234", "ue1");
        ZoneDto mZona2 = new ZoneDto("zona 2", "8262", "ue2");
        ResourceDto mRecurso1 = new ResourceDto("A123", 1);
        ResourceDto mRecurso2 = new ResourceDto("B621", 2);
        ArrayList<ZoneDto> mZonas = new ArrayList<>();
        mZonas.add(mZona1);
        mZonas.add(mZona2);
        ArrayList<ResourceDto> mRecursos = new ArrayList<>();
        mRecursos.add(mRecurso1);
        mRecursos.add(mRecurso2);
        mRoleDto = new RoleDto(mZonas, mRecursos);
        mGetRolesResponse.roles = mRoleDto;
    }

    @Test
    public void testGetCodigoRespuesta() {
        assertTrue(mGetRolesResponse.getCode() == 0);
    }

    @Test
    public void testGetRoles() {
        assertTrue(mGetRolesResponse.getRoles().equals(mRoleDto));
    }
}
