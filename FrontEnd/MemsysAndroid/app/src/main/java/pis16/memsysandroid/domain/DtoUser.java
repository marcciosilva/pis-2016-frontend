package pis16.memsysandroid.domain;

/**
 * DtoUser contiene la información de los usuarios
 * Created by jmsmuy on 21/08/16.
 */

public class DtoUser extends DtoBase {

    private String Contraseña;

    private String NombreUsuario;

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    @Override
    public String toString() {
        return "ClassPojo [Contraseña = " + Contraseña + ", NombreUsuario = " + NombreUsuario + "]";
    }
}
