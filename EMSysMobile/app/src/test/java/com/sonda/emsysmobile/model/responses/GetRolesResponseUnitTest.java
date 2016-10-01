package com.sonda.emsysmobile.model.responses;

import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;
import com.sonda.emsysmobile.model.core.DtoZona;
import com.sonda.emsysmobile.model.responses.GetRolesResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class GetRolesResponseUnitTest {

    private GetRolesResponse mGetRolesResponse;
    private DtoRol mDtoRol;

    @Before
    public void executeBeforeEach() {
        mGetRolesResponse = new GetRolesResponse();
        mGetRolesResponse.mCodigoRespuesta = "0";
        DtoZona mZona1 = new DtoZona("zona 1", "1234", "ue1");
        DtoZona mZona2 = new DtoZona("zona 2", "8262", "ue2");
        DtoRecurso mRecurso1 = new DtoRecurso("A123");
        DtoRecurso mRecurso2 = new DtoRecurso("B621");
        ArrayList<DtoZona> mZonas = new ArrayList<>();
        mZonas.add(mZona1);
        mZonas.add(mZona2);
        ArrayList<DtoRecurso> mRecursos = new ArrayList<>();
        mRecursos.add(mRecurso1);
        mRecursos.add(mRecurso2);
        mDtoRol = new DtoRol(mZonas, mRecursos);
        mGetRolesResponse.mRoles = mDtoRol;
    }

    @Test
    public void testGetCodigoRespuesta() {
        assertTrue(mGetRolesResponse.getCodigoRespuesta().equals("0"));
    }

    @Test
    public void testGetRoles() {
        assertTrue(mGetRolesResponse.getRoles().equals(mDtoRol));
    }

}
