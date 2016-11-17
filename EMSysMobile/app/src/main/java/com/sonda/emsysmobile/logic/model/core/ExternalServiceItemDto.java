package com.sonda.emsysmobile.logic.model.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jmsmuy on 16/10/16.
 */

public class ExternalServiceItemDto implements Serializable {

    @SerializedName("field1")
    private String field1;

    @SerializedName("field2")
    private String field2;

    @SerializedName("field3")
    private String field3;

    @SerializedName("field4")
    private String field4;

    @SerializedName("field5")
    private String field5;

    @SerializedName("field6")
    private String field6;

    @SerializedName("field7")
    private String field7;

    @SerializedName("field8")
    private String field8;

    @SerializedName("field9")
    private String field9;

    @SerializedName("field10")
    private String field10;

    public ExternalServiceItemDto(String field1, String field2, String field3, String field4,
                                  String field5, String field6, String field7, String field8,
                                  String field9, String field10) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
        this.field9 = field9;
        this.field10 = field10;
    }

    public final String getField1() {
        return field1;
    }

    public final void setField1(String field1) {
        this.field1 = field1;
    }

    public final String getField2() {
        return field2;
    }

    public final void setField2(String field2) {
        this.field2 = field2;
    }

    public final String getField3() {
        return field3;
    }

    public final void setField3(String field3) {
        this.field3 = field3;
    }

    public final String getField4() {
        return field4;
    }

    public final void setField4(String field4) {
        this.field4 = field4;
    }

    public final String getField5() {
        return field5;
    }

    public final void setField5(String field5) {
        this.field5 = field5;
    }

    public final String getField6() {
        return field6;
    }

    public final void setField6(String field6) {
        this.field6 = field6;
    }

    public final String getField7() {
        return field7;
    }

    public final void setField7(String field7) {
        this.field7 = field7;
    }

    public final String getField8() {
        return field8;
    }

    public final void setField8(String field8) {
        this.field8 = field8;
    }

    public final String getField9() {
        return field9;
    }

    public final void setField9(String field9) {
        this.field9 = field9;
    }

    public final String getField10() {
        return field10;
    }

    public final void setField10(String field10) {
        this.field10 = field10;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExternalServiceItemDto that = (ExternalServiceItemDto) o;

        if (field1 != null ? !field1.equals(that.field1) : that.field1 != null) {
            return false;
        }
        if (field2 != null ? !field2.equals(that.field2) : that.field2 != null) {
            return false;
        }
        if (field3 != null ? !field3.equals(that.field3) : that.field3 != null) {
            return false;
        }
        if (field4 != null ? !field4.equals(that.field4) : that.field4 != null) {
            return false;
        }
        if (field5 != null ? !field5.equals(that.field5) : that.field5 != null) {
            return false;
        }
        if (field6 != null ? !field6.equals(that.field6) : that.field6 != null) {
            return false;
        }
        if (field7 != null ? !field7.equals(that.field7) : that.field7 != null) {
            return false;
        }
        if (field8 != null ? !field8.equals(that.field8) : that.field8 != null) {
            return false;
        }
        if (field9 != null ? !field9.equals(that.field9) : that.field9 != null) {
            return false;
        }
        return field10 != null ? field10.equals(that.field10) : that.field10 == null;

    }

}
