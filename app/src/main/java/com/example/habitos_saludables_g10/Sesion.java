package com.example.habitos_saludables_g10;

public class Sesion {
    private String idSesion;
    private String idUsuario;

    public Sesion() {
    }

    public Sesion(String idSesion, String idUsuario) {
        this.idSesion = idSesion;
        this.idUsuario = idUsuario;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
