package com.sonda.emsysmobile.database.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by marccio on 15-Sep-16.
 */
@Table(name = "SampleReferencedTable")
public class SampleReferencedTable extends Model {
    // Name tiene que ser unico (no es primary key porque Active Android ya lo maneja de manera
    // interna), por lo que esto es una pseudo primary key.
    // Si en un INSERT agregamos una tupla con el mismo nombre, se reemplaza la tupla en la base.
    @Column(name = "Name", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String name;

    public SampleReferencedTable() {
        super();
    }

    public SampleReferencedTable(String name) {
        super();
        this.name = name;
    }

    // Metodo opcional para obtener tuplas asociadas a una tupla de esta tabla.
    public final List<SampleTable> items() {
        return getMany(SampleTable.class, "SampleReferencedTable");
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!SampleReferencedTable.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final SampleReferencedTable other = (SampleReferencedTable) obj;
        return (super.equals(other) && name.equals(other.getName()));
    }

}
