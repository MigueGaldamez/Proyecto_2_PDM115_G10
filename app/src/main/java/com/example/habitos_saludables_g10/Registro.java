package com.example.habitos_saludables_g10;

import java.io.Serializable;

public class Registro implements Serializable {

    private String idDia,idHabito,idUsuario,fecha,comentario;

    public Registro() {
    }

    public Registro(String idDia, String idHabito, String idUsuario, String fecha, String comentario) {
        this.idDia = idDia;
        this.idHabito = idHabito;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public String getIdDia() {
        return idDia;
    }

    public void setIdDia(String idDia) {
        this.idDia = idDia;
    }

    public String getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(String idHabito) {
        this.idHabito = idHabito;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
