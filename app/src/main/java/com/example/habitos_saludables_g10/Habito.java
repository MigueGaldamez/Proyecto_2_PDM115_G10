package com.example.habitos_saludables_g10;

public class Habito {
    private String idHabito;
    private String nomHabito;
    private String fechaCreacion;
    private String objetivo;
    private String idUsuario;

    public Habito() {
    }

    public Habito(String idHabito, String nomHabito, String fechaCreacion, String objetivo, String idUsuario) {
        this.idHabito = idHabito;
        this.nomHabito = nomHabito;
        this.fechaCreacion = fechaCreacion;
        this.objetivo = objetivo;
        this.idUsuario = idUsuario;
    }

    public String getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(String idHabito) {
        this.idHabito = idHabito;
    }

    public String getNomHabito() {
        return nomHabito;
    }

    public void setNomHabito(String nomHabito) {
        this.nomHabito = nomHabito;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
