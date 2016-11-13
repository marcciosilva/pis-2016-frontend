package com.sonda.emsysmobile.database.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nachoprbd on 10/11/2016.
 */
public class SampleReferencedTableTest {
    private SampleReferencedTable mSampleReferencedTable;
    private SampleReferencedTable testSampleReferencedTable;

    @Before
    public void setUp(){
        mSampleReferencedTable = new SampleReferencedTable();
        mSampleReferencedTable.setName("reference1");
        mSampleReferencedTable.save();
        testSampleReferencedTable = new SampleReferencedTable();
        mSampleReferencedTable.setName("reference1");
        testSampleReferencedTable.save();
    }

    @Test
    public void getName_CompareWithSameName_ReturnsTrue() {
        assertTrue(mSampleReferencedTable.getName().equals("reference1"));
    }

    @Test
    public void setName_DifferentName_ReturnsTrue() {
        mSampleReferencedTable.setName("reference2");
        mSampleReferencedTable.save();
        assertTrue(mSampleReferencedTable.getName().equals("reference2"));
    }

    @Test
    public void setName_CompareWithDifferentName_ReturnsFalse() {
        mSampleReferencedTable.setName("reference1");
        mSampleReferencedTable.save();
        testSampleReferencedTable.setName("reference2");
        testSampleReferencedTable.save();
        assertFalse(mSampleReferencedTable.equals(testSampleReferencedTable));
    }

}
