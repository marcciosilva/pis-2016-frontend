package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class ExternalServiceResponseTest {

    private ExternalServiceResponse mExternalServiceResponse;
    private ExternalServiceItemDto mExternalServiceItemDto;
    private List<ExternalServiceItemDto> mList;
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
        mList = new ArrayList<>();
        mList.add(mExternalServiceItemDto);
        mExternalServiceResponse = new ExternalServiceResponse(mList);
    }

    @Test
    public void getItems() throws Exception {
        assertTrue(mExternalServiceResponse.getItems().equals(mList));
    }

    @Test
    public void setItems() throws Exception {
        mExternalServiceResponse.setItems(new ArrayList<ExternalServiceItemDto>());
        assertTrue(mExternalServiceResponse.getItems().equals(new
                ArrayList<ExternalServiceItemDto>()));
    }

}