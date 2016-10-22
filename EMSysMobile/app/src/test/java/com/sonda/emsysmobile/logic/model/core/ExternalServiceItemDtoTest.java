package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by marccio on 10/21/16.
 */
public class ExternalServiceItemDtoTest {

    private ExternalServiceItemDto mExternalServiceItemDto;
    private String mField1;
    private String mField2;
    private String mField3;
    private String mField4;
    private String mField5;
    private String mField6;
    private String mField7;
    private String mField8;
    private String mField9;
    private String mField10;

    @Before
    public void setUp() throws Exception {
        mField1 = "field1";
        mField2 = "field2";
        mField3 = "field3";
        mField4 = "field4";
        mField5 = "field5";
        mField6 = "field6";
        mField7 = "field7";
        mField8 = "field8";
        mField9 = "field9";
        mField10 = "field10";
        mExternalServiceItemDto = new ExternalServiceItemDto(mField1, mField2, mField3, mField4,
                mField5, mField6, mField7, mField8, mField9, mField10);
    }

    @Test
    public void getField1() throws Exception {
        assertTrue(mExternalServiceItemDto.getField1().equals(mField1));
    }

    @Test
    public void setField1() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField1(testString);
        assertTrue(mExternalServiceItemDto.getField1().equals(testString));
    }

    @Test
    public void getField2() throws Exception {
        assertTrue(mExternalServiceItemDto.getField2().equals(mField2));
    }

    @Test
    public void setField2() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField2(testString);
        assertTrue(mExternalServiceItemDto.getField2().equals(testString));
    }

    @Test
    public void getField3() throws Exception {
        assertTrue(mExternalServiceItemDto.getField3().equals(mField3));
    }

    @Test
    public void setField3() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField3(testString);
        assertTrue(mExternalServiceItemDto.getField3().equals(testString));
    }

    @Test
    public void getField4() throws Exception {
        assertTrue(mExternalServiceItemDto.getField4().equals(mField4));
    }

    @Test
    public void setField4() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField4(testString);
        assertTrue(mExternalServiceItemDto.getField4().equals(testString));
    }

    @Test
    public void getField5() throws Exception {
        assertTrue(mExternalServiceItemDto.getField5().equals(mField5));
    }

    @Test
    public void setField5() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField5(testString);
        assertTrue(mExternalServiceItemDto.getField5().equals(testString));
    }

    @Test
    public void getField6() throws Exception {
        assertTrue(mExternalServiceItemDto.getField6().equals(mField6));
    }

    @Test
    public void setField6() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField6(testString);
        assertTrue(mExternalServiceItemDto.getField6().equals(testString));
    }

    @Test
    public void getField7() throws Exception {
        assertTrue(mExternalServiceItemDto.getField7().equals(mField7));
    }

    @Test
    public void setField7() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField7(testString);
        assertTrue(mExternalServiceItemDto.getField7().equals(testString));
    }

    @Test
    public void getField8() throws Exception {
        assertTrue(mExternalServiceItemDto.getField8().equals(mField8));
    }

    @Test
    public void setField8() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField8(testString);
        assertTrue(mExternalServiceItemDto.getField8().equals(testString));
    }

    @Test
    public void getField9() throws Exception {
        assertTrue(mExternalServiceItemDto.getField9().equals(mField9));
    }

    @Test
    public void setField9() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField9(testString);
        assertTrue(mExternalServiceItemDto.getField9().equals(testString));
    }

    @Test
    public void getField10() throws Exception {
        assertTrue(mExternalServiceItemDto.getField10().equals(mField10));
    }

    @Test
    public void setField10() throws Exception {
        String testString = "testString";
        mExternalServiceItemDto.setField10(testString);
        assertTrue(mExternalServiceItemDto.getField10().equals(testString));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mExternalServiceItemDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto recurso = new ResourceDto(null, 0);
        assertFalse(mExternalServiceItemDto.equals(recurso));
    }

    @Test
    public void equals_DifferentField1_ReturnsFalse() {
        String testField1 = "field11";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField2_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field22";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField3_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field33";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField4_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field44";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField5_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field55";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField6_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field66";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField7_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field77";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField8_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field88";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField9_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field99";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


    @Test
    public void equals_DifferentField10_ReturnsFalse() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field1010";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertFalse(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        String testField1 = "field1";
        String testField2 = "field2";
        String testField3 = "field3";
        String testField4 = "field4";
        String testField5 = "field5";
        String testField6 = "field6";
        String testField7 = "field7";
        String testField8 = "field8";
        String testField9 = "field9";
        String testField10 = "field10";
        ExternalServiceItemDto testExternalServiceItemDto = new ExternalServiceItemDto(testField1,
                testField2, testField3, testField4, testField5, testField6, testField7, testField8,
                testField9, testField10);
        assertTrue(testExternalServiceItemDto.equals(mExternalServiceItemDto));
    }


}