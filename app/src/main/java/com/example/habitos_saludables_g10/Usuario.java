package com.example.habitos_saludables_g10;

import java.util.Date;

public class Usuario {

    private String idUsuario;
    private String nomUsuario;
    private String fechaUnion;
    private String correo;
    private String urlImagen;

    public Usuario() {
    }

    public Usuario(String idUsuario, String nomUsuario, String fechaUnion, String correo, String urlImagen) {
        this.idUsuario = idUsuario;
        this.nomUsuario = nomUsuario;
        this.fechaUnion = fechaUnion;
        this.correo = correo;
        this.urlImagen = urlImagen;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getFechaUnion() {
        return fechaUnion;
    }

    public void setFechaUnion(String fechaUnion) {
        this.fechaUnion = fechaUnion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
