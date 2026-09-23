package model;

import java.util.Date;

public class PeriodoMatricula {
    private String semestre;
    private Date dataInicio;
    private Date dataFim;
    private boolean encerrado;

    public PeriodoMatricula() {
    }

    public PeriodoMatricula(String semestre, Date dataInicio, Date dataFim) {
        this.semestre = semestre;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = false;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public boolean estaAberto() {
        if (encerrado) {
            return false;
        }
        Date agora = new Date();
        return dataInicio != null && dataFim != null
                && !agora.before(dataInicio) && !agora.after(dataFim);
    }

    public void encerrar() {
        this.encerrado = true;
    }
}
