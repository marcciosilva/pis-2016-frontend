package com.sonda.emsysmobile.backendcommunication.services.request;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by marccio on 10/22/16.
 */
public class ExternalServiceRequestTest extends BaseMockTest {

    private ExternalServiceRequest<Integer> mExternalServiceRequest;

    ExternalServiceQueryDto mExternalServiceQueryDto;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mExternalServiceQueryDto = new
                ExternalServiceQueryDto("parameter_1", "parameter_2", "parameter_3");
        mExternalServiceRequest = new ExternalServiceRequest<>(context, EmsysResponse.class,
                mExternalServiceQueryDto);
    }

    @Test
    public void getPath() throws Exception {
        mExternalServiceRequest.getPath().equals(ExternalServiceRequest.CONSUME_WS_PATH);
    }

    @Test
    public void getBody() throws Exception {
        // TODO mejorar.
        mExternalServiceRequest.getBody();
    }

}