package com.sonda.emsysmobile.persistence;

import android.util.Log;

import com.activeandroid.query.Select;
import com.sonda.emsysmobile.persistence.model.SampleReferencedTable;
import com.sonda.emsysmobile.persistence.model.SampleTable;

import java.util.List;

/**
 * Created by marccio on 15-Sep-16.
 */
public final class DatabaseTest {

    private static final String TAG = DatabaseTest.class.getName();
    private static boolean seedDatabase = true;

    private DatabaseTest() {

    }

    /**
     * La idea de este test es ejecutar algunos inserts (en activeAndroidSeed), y probar
     * hacer un SELECT con Active Android.
     */
    public static void executeTest() {
        if (seedDatabase) {
            activeAndroidSeed();
        }
        activeAndroidGetItemsForCategories();
    }


    private static void activeAndroidGetItemsForCategories() {
        List<SampleReferencedTable> refTuples = new Select().from(SampleReferencedTable.class).orderBy("Name ASC").execute();
        for (SampleReferencedTable refTuple : refTuples) {
            List<SampleTable> tuples = getAll(refTuple);
            Log.d(TAG, "#### Items under \"" + refTuple.getName() + "\" refTuple.####");
            for (SampleTable tuple : tuples) {
                Log.d(TAG, tuple.getName());
            }
        }
    }

    private static void activeAndroidSeed() {
        SampleReferencedTable sampleRefTableTuple = new SampleReferencedTable();
        sampleRefTableTuple.setName("refTuple");
        sampleRefTableTuple.save();
        SampleTable sampleTableTuple = new SampleTable();
        sampleTableTuple.setSampleReferencedTable(sampleRefTableTuple);
        sampleTableTuple.setName("tuple1");
        sampleTableTuple.save();
        sampleTableTuple = new SampleTable();
        sampleTableTuple.setSampleReferencedTable(sampleRefTableTuple);
        sampleTableTuple.setName("tuple2");
        sampleTableTuple.save();
        sampleTableTuple = new SampleTable();
        sampleTableTuple.setSampleReferencedTable(sampleRefTableTuple);
        sampleTableTuple.setName("tuple3");
        sampleTableTuple.save();
        Log.d(TAG, "Add some tuples.");
    }

    public static List<SampleTable> getAll(SampleReferencedTable sampleRefTable) {
        return new Select()
                .from(SampleTable.class)
                .where("SampleReferencedTable = ?", sampleRefTable.getId())
                .orderBy("Name ASC")
                .execute();
    }

}


