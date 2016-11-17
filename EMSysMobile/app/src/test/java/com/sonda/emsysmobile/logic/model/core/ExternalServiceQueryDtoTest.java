package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class ExternalServiceQueryDtoTest {

    private ExternalServiceQueryDto mExternalQueryDto;
    private String mParameter1;
    private String mParameter2;
    private String mParameter3;

    @Before
    public void setUp() throws Exception {
        mParameter1 = "base_parameter_1";
        mParameter2 = "base_parameter_2";
        mParameter3 = "base_parameter_3";
        mExternalQueryDto = new ExternalServiceQueryDto(mParameter1, mParameter2, mParameter3);
    }

    @Test
    public void getParameter1() throws Exception {
        assertTrue(mExternalQueryDto.getParameter1().equals(mParameter1));
    }

    @Test
    public void setParameter1() throws Exception {
        String testParameter = "test_parameter";
        mExternalQueryDto.setParameter1(testParameter);
        assertTrue(mExternalQueryDto.getParameter1().equals(testParameter));
    }

    @Test
    public void getParameter2() throws Exception {
        assertTrue(mExternalQueryDto.getParameter2().equals(mParameter2));

    }

    @Test
    public void setParameter2() throws Exception {
        String testParameter = "test_parameter";
        mExternalQueryDto.setParameter2(testParameter);
        assertTrue(mExternalQueryDto.getParameter2().equals(testParameter));
    }

    @Test
    public void getParameter3() throws Exception {
        assertTrue(mExternalQueryDto.getParameter3().equals(mParameter3));

    }

    @Test
    public void setParameter3() throws Exception {
        String testParameter = "test_parameter";
        mExternalQueryDto.setParameter3(testParameter);
        assertTrue(mExternalQueryDto.getParameter3().equals(testParameter));
    }

}