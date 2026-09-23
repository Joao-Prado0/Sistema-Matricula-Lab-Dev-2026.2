package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.StatusMatricula;
import enums.TipoInscricao;
import interfaces.SistemaCobranca;

public class Matricula {
    private Date data;
    private String semestre;
    private StatusMatricula status;
    private Aluno aluno;
    private PeriodoMatricula periodo;
    private final List<Inscricao> inscricoes = new ArrayList<>();
    private SistemaCobranca sistemaCobranca;

    public static final int QTD_OBRIGATORIAS = 4;
    public static final int QTD_OPTATIVAS = 2;

    public Matricula() {
        this.status = StatusMatricula.EM_ANDAMENTO;
    }

    public Matricula(Aluno aluno, String semestre, PeriodoMatricula periodo) {
        this.aluno = aluno;
        this.semestre = semestre;
        this.periodo = periodo;
        this.data = new Date();
        this.status = StatusMatricula.EM_ANDAMENTO;
    }

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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public PeriodoMatricula getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoMatricula periodo) {
        this.periodo = periodo;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setSistemaCobranca(SistemaCobranca sistemaCobranca) {
        this.sistemaCobranca = sistemaCobranca;
    }

    private int contarPorTipo(TipoInscricao tipo) {
        int total = 0;
        for (Inscricao i : inscricoes) {
            if (!i.isCancelada() && i.getTipo() == tipo) {
                total++;
            }
        }
        return total;
    }

    public void adicionarInscricao(Disciplina d, TipoInscricao tipo) {
        if (periodo != null && !periodo.estaAberto()) {
            throw new IllegalStateException("Periodo de matricula fechado.");
        }
        if (d.estaLotada()) {
            throw new IllegalStateException("Disciplina " + d.getCodigo() + " esta lotada.");
        }
        if (tipo == TipoInscricao.OBRIGATORIA && contarPorTipo(TipoInscricao.OBRIGATORIA) >= QTD_OBRIGATORIAS) {
            throw new IllegalStateException("Limite de " + QTD_OBRIGATORIAS + " disciplinas obrigatorias atingido.");
        }
        if (tipo == TipoInscricao.OPTATIVA && contarPorTipo(TipoInscricao.OPTATIVA) >= QTD_OPTATIVAS) {
            throw new IllegalStateException("Limite de " + QTD_OPTATIVAS + " disciplinas optativas atingido.");
        }
        Inscricao inscricao = new Inscricao(d, aluno, tipo);
        inscricoes.add(inscricao);
        d.adicionarInscricao(inscricao);
    }

    public void removerInscricao(Disciplina d) {
        if (periodo != null && !periodo.estaAberto()) {
            throw new IllegalStateException("Periodo de matricula fechado.");
        }
        for (Inscricao i : inscricoes) {
            if (i.getDisciplina() == d && !i.isCancelada()) {
                i.cancelar();
                if (sistemaCobranca != null) {
                    sistemaCobranca.notificarMatricula(this);
                }
                return;
            }
        }
    }

    public boolean validar() {
        int obrigatorias = contarPorTipo(TipoInscricao.OBRIGATORIA);
        int optativas = contarPorTipo(TipoInscricao.OPTATIVA);
        return obrigatorias > 0 && obrigatorias <= QTD_OBRIGATORIAS && optativas <= QTD_OPTATIVAS;
    }

    public void confirmar() {
        if (!validar()) {
            throw new IllegalStateException("Matricula invalida: verifique os limites de disciplinas.");
        }
        this.status = StatusMatricula.CONFIRMADA;
        if (sistemaCobranca != null) {
            sistemaCobranca.notificarMatricula(this);
        }
    }

    public void cancelar() {
        for (Inscricao i : inscricoes) {
            if (!i.isCancelada()) {
                i.cancelar();
            }
        }
        this.status = StatusMatricula.CANCELADA;
        if (sistemaCobranca != null) {
            sistemaCobranca.notificarMatricula(this);
        }
    }
}
