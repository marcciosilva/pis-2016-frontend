package com.sonda.emsysmobile.persistence.model;

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
    public String name;

    // Metodo opcional para obtener tuplas asociadas a una tupla de esta tabla.
    public List<SampleTable> items() {
        return getMany(SampleTable.class, "SampleReferencedTable");
    }
}
