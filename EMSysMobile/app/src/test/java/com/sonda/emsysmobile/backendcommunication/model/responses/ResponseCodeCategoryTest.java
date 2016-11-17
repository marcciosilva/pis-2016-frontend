package com.sonda.emsysmobile.backendcommunication.model.responses;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 13-Oct-16.
 */
public class ResponseCodeCategoryTest {
    @Test
    public void getNumVal() throws Exception {
        ErrorCodeCategory code = ErrorCodeCategory.INVALID_CREDENTIALS;
        assertTrue(code.getNumVal() == ErrorCodeCategory.INVALID_CREDENTIALS.getNumVal());
    }

}