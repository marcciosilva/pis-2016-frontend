package com.sonda.emsysmobile.backendcommunication.model.responses;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Pape on 11/11/2016.
 */

public class ReportTimeResponseTest {

    private ReportTimeResponse mReportTimeResponse;
    private ErrorResponse mInnerResponse;

    @Before
    public void setUp() {
        mReportTimeResponse = new ReportTimeResponse();
        mInnerResponse = new ErrorResponse();
        mInnerResponse.setMsg("errorMsg");
        mReportTimeResponse.setInnerResponse(mInnerResponse);
        mReportTimeResponse.setCode(0);
    }

    /**
     * Prueba get de respuesta interna.
     */
    @Test
    public void getInnerResponse_CompareWithSameInnerResponse_ReturnsTrue() {
        assertTrue(mReportTimeResponse.getInnerResponse().equals(mInnerResponse));
    }

    /**
     * Prueba seteo de respuesta interna.
     */
    @Test
    public void setInnerResponse_Null_ReturnsTrue() {
        mReportTimeResponse.setInnerResponse(null);
        assertTrue(mReportTimeResponse.getInnerResponse() == null);
    }

    /**
     * Prueba equals en caso de comparar con algo en null.
     */
    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mReportTimeResponse.equals(null));
    }

    /**
     * Prueba equals en caso de comparar otro tipo de objeto.
     */
    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(mReportTimeResponse.equals(new Object()));
    }

    /**
     * Prueba equals en caso exitoso.
     */
    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        ReportTimeResponse testResponse = new ReportTimeResponse();
        testResponse.setCode(0);
        ErrorResponse testInnerResponse = new ErrorResponse();
        testInnerResponse.setMsg("errorMsg");
        testResponse.setInnerResponse(testInnerResponse);
        assertTrue(mReportTimeResponse.equals(testResponse));
    }
}
