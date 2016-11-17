package com.sonda.emsysmobile.backendcommunication.model.responses;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Pape on 11/11/2016.
 */

public class UpdateGeoLocationResponseTest {

    private UpdateGeoLocationResponse mUpdateGeoLocResponse;
    private ErrorResponse mInnerResponse;

    @Before
    public void setUp() {
        mUpdateGeoLocResponse = new UpdateGeoLocationResponse();
        mInnerResponse = new ErrorResponse();
        mInnerResponse.setMsg("errorMsg");
        mUpdateGeoLocResponse.setInnerResponse(mInnerResponse);
        mUpdateGeoLocResponse.setCode(0);
    }

    /**
     * Prueba get de respuesta interna.
     */
    @Test
    public void getInnerResponse_CompareWithSameInnerResponse_ReturnsTrue() {
        assertTrue(mUpdateGeoLocResponse.getInnerResponse().equals(mInnerResponse));
    }

    /**
     * Prueba seteo de respuesta interna.
     */
    @Test
    public void setInnerResponse_Null_ReturnsTrue() {
        mUpdateGeoLocResponse.setInnerResponse(null);
        assertTrue(mUpdateGeoLocResponse.getInnerResponse() == null);
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mUpdateGeoLocResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mUpdateGeoLocResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        UpdateGeoLocationResponse testResponse = new UpdateGeoLocationResponse();
        testResponse.setCode(0);
        ErrorResponse testInnerResponse = new ErrorResponse();
        testInnerResponse.setMsg("errorMsg");
        testResponse.setInnerResponse(testInnerResponse);
        assertTrue(mUpdateGeoLocResponse.equals(testResponse));
    }
}
