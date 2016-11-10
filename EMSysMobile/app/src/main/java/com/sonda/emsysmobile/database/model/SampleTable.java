package com.sonda.emsysmobile.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by marccio on 15-Sep-16.
 */
@Table(name = "SampleTable")
public class SampleTable extends Model {
    // Name tiene que ser unico (no es primary key porque Active Android ya lo maneja de manera
    // interna). Si en un INSERT agregamos una tupla con el mismo nombre, se reemplaza la tupla
    // en la base.
    @Column(name = "Name", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String name;

    @Column(name = "SampleReferencedTable")
    private SampleReferencedTable sampleReferencedTable;

    public SampleTable() {
        super();
    }

    public SampleTable(String name, SampleReferencedTable sampleReferencedTable) {
        super();
        this.name = name;
        this.sampleReferencedTable = sampleReferencedTable;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final SampleReferencedTable getSampleReferencedTable() {
        return sampleReferencedTable;
    }

    public final void setSampleReferencedTable(SampleReferencedTable sampleReferencedTable) {
        this.sampleReferencedTable = sampleReferencedTable;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!SampleTable.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final SampleTable other = (SampleTable) obj;
        return (super.equals(other) && name.equals(other.getName()) && sampleReferencedTable.equals(other.getSampleReferencedTable()));
    }
}