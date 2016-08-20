package com.example.andres.myfirstapplication.Models;

public class Usuario
{
    private String Contraseña;

    private String NombreUsuario;

    public String getContraseña ()
    {
        return Contraseña;
    }

    public void setContraseña (String Contraseña)
    {
        this.Contraseña = Contraseña;
    }

    public String getNombreUsuario ()
    {
        return NombreUsuario;
    }

    public void setNombreUsuario (String NombreUsuario)
    {
        this.NombreUsuario = NombreUsuario;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Contraseña = "+Contraseña+", NombreUsuario = "+NombreUsuario+"]";
    }
}

