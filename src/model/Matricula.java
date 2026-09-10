package model;

import java.util.Date;

import enums.StatusMatricula;

public class Matricula {
    private Date data;
    private String semestre;
    private StatusMatricula status;
    public static final int QTD_OBRIGATORIAS = 4;
    public static final int QTD_OPTATIVAS = 2;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public void adicionarInscricao(Disciplina d, TipoInscricao tipo) {
    }

    public void removerInscricao(Disciplina d) {
    }

    public boolean validar() {
        return false;
    }

    public void confirmar() {
    }

    public void cancelar() {
    }
}