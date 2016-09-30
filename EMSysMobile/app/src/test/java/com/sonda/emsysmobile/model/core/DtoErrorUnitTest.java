package com.sonda.emsysmobile.model.core;

import com.sonda.emsysmobile.model.core.DtoError;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 9/30/16.
 */

public class DtoErrorUnitTest {

    private DtoError mDtoError;

    @Before
    public void executeBeforeEach() {
        mDtoError = new DtoError("1234");
    }

    @Test
    public void testGetCodigo() {
        assertTrue(mDtoError.getCodigo().equals("1234"));
    }

    @Test
    public void testSetCodigo() {
        mDtoError.setCodigo("4321");
        assertTrue(mDtoError.getCodigo().equals("4321"));
    }

}
