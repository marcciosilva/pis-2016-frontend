package com.sonda.emsysmobile.database.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nachoprbd on 10/11/2016.
 */
public class SampleTableTest {

    private SampleTable mSampleTable;
    private SampleReferencedTable mSampleReferencedTable;
    private SampleTable testSampleTable;

    @Before
    public void setUp(){
        mSampleReferencedTable = new SampleReferencedTable();
        mSampleReferencedTable.setName("reference1");
        mSampleTable = new SampleTable("table1", mSampleReferencedTable);
        testSampleTable = new SampleTable("table1", mSampleReferencedTable);
    }

    @Test
    public void getName_CompareWithSameName_ReturnsTrue() {
        assertTrue(mSampleTable.getName().equals("table1"));
    }

    @Test
    public void getSampleReferencedTable_CompareWithSameReferencedTable_ReturnsTrue() {
        assertTrue(mSampleTable.getSampleReferencedTable().equals(mSampleReferencedTable));
    }

    @Test
    public void setName_DifferentName_ReturnsTrue() {
        mSampleTable.setName("table2");
        assertTrue(mSampleTable.getName().equals("table2"));
    }

    @Test
    public void setSampleReferencedTable_DifferentSampleReferencedTable_ReturnsTrue() {
        SampleReferencedTable sampleReferencedTable = new SampleReferencedTable();
        sampleReferencedTable.setName("reference2");
        mSampleTable.setSampleReferencedTable(sampleReferencedTable);
        assertTrue(mSampleTable.getSampleReferencedTable().equals(sampleReferencedTable));
    }

    @Test
    public void setName_CompareWithDifferentName_ReturnsFalse() {
        mSampleTable.setName("table1");
        testSampleTable.setName("table2");
        assertFalse(mSampleTable.equals(testSampleTable));
    }

    @Test
    public void setSampleReferencedTable_CompareWithDifferentSampleReferencedTable_ReturnsFalse() {
        SampleReferencedTable sampleReferencedTable = new SampleReferencedTable();
        sampleReferencedTable.setName("reference1");
        mSampleTable.setSampleReferencedTable(sampleReferencedTable);
        SampleReferencedTable sampleReferencedTable2 = new SampleReferencedTable();
        sampleReferencedTable2.setName("reference2");
        testSampleTable.setSampleReferencedTable(sampleReferencedTable2);
        assertFalse(mSampleTable.equals(testSampleTable));
    }
}
