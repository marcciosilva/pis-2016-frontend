package com.sonda.emsysmobile.persistence.model;

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
    public String name;

    @Column(name = "SampleReferencedTable")
    public SampleReferencedTable sampleReferencedTable;

    public SampleTable() {
        super();
    }

    public SampleTable(String name, SampleReferencedTable sampleReferencedTable) {
        super();
        this.name = name;
        this.sampleReferencedTable = sampleReferencedTable;
    }
}