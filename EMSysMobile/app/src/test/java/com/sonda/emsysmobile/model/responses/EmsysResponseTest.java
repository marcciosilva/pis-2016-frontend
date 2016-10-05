package com.sonda.emsysmobile.model.responses;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 10/5/16.
 */

public class EmsysResponseTest {

    private EmsysResponse mEmsysResponse;
    private int mResponseCode;

    @Before
    public void executeBeforeEach() {
        mEmsysResponse = new EmsysResponse();
        mResponseCode = 1;
        mEmsysResponse.setCode(mResponseCode);
    }

    /**
     * Prueba que el get del code funcione bien.
     */
    @Test
    public void getCode_SameCode_ReturnsTrue() {
        assertTrue(mEmsysResponse.getCode() == mResponseCode);
    }

    /**
     * Prueba que el seteo del code se haga correctamente.
     */
    @Test
    public void setCodigo_ChangeCode_ReturnsTrue() {
        mEmsysResponse.setCode(2);
        assertTrue(mEmsysResponse.getCode() == 2);
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mEmsysResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mEmsysResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_SameFields_ReturnsTrue() {
        EmsysResponse testResponse = new EmsysResponse();
        testResponse.setCode(mResponseCode);
        assertTrue(mEmsysResponse.equals(testResponse));
    }

}
