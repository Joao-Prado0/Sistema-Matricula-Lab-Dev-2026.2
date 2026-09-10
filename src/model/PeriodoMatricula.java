package model;

import java.util.Date;

public class PeriodoMatricula {
    private String semestre;
    private Date dataInicio;
    private Date dataFim;

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

    public boolean estaAberto() {
        return false;
    }

    public void encerrar() {
    }
}