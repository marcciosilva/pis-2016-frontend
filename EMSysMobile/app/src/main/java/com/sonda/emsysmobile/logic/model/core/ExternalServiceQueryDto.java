package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jmsmuy on 16/10/16.
 */

public class ExternalServiceQueryDto {

    @SerializedName("param1")
    private String parameter1;

    @SerializedName("param2")
    private String parameter2;

    @SerializedName("param3")
    private String parameter3;

    public ExternalServiceQueryDto(String parameter1, String parameter2, String parameter3) {
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.parameter3 = parameter3;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }
}
