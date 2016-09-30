package com.sonda.emsysmobile.model.core;

import com.sonda.emsysmobile.model.core.DtoOk;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class DtoOkUnitTest {


    private DtoOk mDtoOk;

    @Before
    public void executeBeforeEach() {
        mDtoOk = new DtoOk("1234");
    }

    @Test
    public void testGetNombre() {
        assertTrue(mDtoOk.getNombre().equals("1234"));
    }

    @Test
    public void testSetNombre() {
        mDtoOk.setNombre("4321");
        assertTrue(mDtoOk.getNombre().equals("4321"));
    }

}
