package com.sonda.emsysmobile.model.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class RoleDtoUnitTest {

    private RoleDto mRoleDto;
    private ZoneDto mZona1;
    private ZoneDto mZona2;
    private ResourceDto mRecurso1;
    private ResourceDto mRecurso2;
    private ArrayList<ZoneDto> mZonas;
    ArrayList<ResourceDto> mRecursos;

    @Before
    public void executeBeforeEach() {
        ZoneDto mZona1 = new ZoneDto("zona 1", 1234, "ue1");
        ZoneDto mZona2 = new ZoneDto("zona 2", 8262, "ue2");
        ResourceDto mRecurso1 = new ResourceDto("A123", 1);
        ResourceDto mRecurso2 = new ResourceDto("B621", 1);
        mZonas = new ArrayList<>();
        mZonas.add(mZona1);
        mZonas.add(mZona2);
        mRecursos = new ArrayList<>();
        mRecursos.add(mRecurso1);
        mRecursos.add(mRecurso2);
        mRoleDto = new RoleDto(mZonas, mRecursos);
    }

    @Test
    public void getZones_SameZones_ReturnsTrue() {
        ArrayList<ZoneDto> zonas = mRoleDto.getZones();
        assertTrue(zonas.equals(mZonas));
    }

    @Test
    public void getResources_SameResources_ReturnsTrue() {
        ArrayList<ResourceDto> recursos = mRoleDto.getResources();
        assertTrue(recursos.equals(mRecursos));
    }

    @Test
    public void setZones_DifferentZones_ReturnsTrue() {
        mZonas.add(new ZoneDto("zona 3", 5312, "ue1"));
        mRoleDto.setZones(mZonas);
        assertTrue(mRoleDto.getZones().equals(mZonas));
    }

    @Test
    public void setResources_DifferentResource_ReturnsTrue() {
        mRecursos.add(new ResourceDto("9875", 1));
        mRoleDto.setResources(mRecursos);
        assertTrue(mRoleDto.getResources().equals(mRecursos));
    }

}
