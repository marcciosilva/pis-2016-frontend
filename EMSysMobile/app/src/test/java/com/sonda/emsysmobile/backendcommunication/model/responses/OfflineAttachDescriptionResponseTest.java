package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Pape on 11/11/2016.
 */

public class OfflineAttachDescriptionResponseTest {

    private OfflineAttachDescriptionResponse mOfflineAttachDescResponse;
    private ErrorResponse mInnerResponse;

    @Before
    public void setUp() {
        mOfflineAttachDescResponse = new OfflineAttachDescriptionResponse();
        mInnerResponse = new ErrorResponse();
        mInnerResponse.setMsg("errorMsg");
        mOfflineAttachDescResponse.setInnerResponse(mInnerResponse);
        mOfflineAttachDescResponse.setCode(0);
    }

    /**
     * Prueba get de respuesta interna.
     */
    @Test
    public void getInnerResponse_CompareWithSameInnerResponse_ReturnsTrue() {
        assertTrue(mOfflineAttachDescResponse.getInnerResponse().equals(mInnerResponse));
    }

    /**
     * Prueba seteo de respuesta interna.
     */
    @Test
    public void setInnerResponse_Null_ReturnsTrue() {
        mOfflineAttachDescResponse.setInnerResponse(null);
        assertTrue(mOfflineAttachDescResponse.getInnerResponse() == null);
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mOfflineAttachDescResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mOfflineAttachDescResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        OfflineAttachDescriptionResponse testResponse = new OfflineAttachDescriptionResponse();
        testResponse.setCode(0);
        ErrorResponse testInnerResponse = new ErrorResponse();
        testInnerResponse.setMsg("errorMsg");
        testResponse.setInnerResponse(testInnerResponse);
        assertTrue(mOfflineAttachDescResponse.equals(testResponse));
    }
}
