package com.example.habitos_saludables_g10;

public class Horario {
    private String idHorario, lun, mar, mie,jue,vie,sab,dom,hora,idHabito;

    public Horario() {
    }

    public Horario(String idHorario, String lun, String mar, String mie, String jue, String vie, String sab, String dom, String hora, String idHabito) {
        this.idHorario = idHorario;
        this.lun = lun;
        this.mar = mar;
        this.mie = mie;
        this.jue = jue;
        this.vie = vie;
        this.sab = sab;
        this.dom = dom;
        this.hora = hora;
        this.idHabito = idHabito;
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public String getLun() {
        return lun;
    }

    public void setLun(String lun) {
        this.lun = lun;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getMie() {
        return mie;
    }

    public void setMie(String mie) {
        this.mie = mie;
    }

    public String getJue() {
        return jue;
    }

    public void setJue(String jue) {
        this.jue = jue;
    }

    public String getVie() {
        return vie;
    }

    public void setVie(String vie) {
        this.vie = vie;
    }

    public String getSab() {
        return sab;
    }

    public void setSab(String sab) {
        this.sab = sab;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(String idHabito) {
        this.idHabito = idHabito;
    }
}
