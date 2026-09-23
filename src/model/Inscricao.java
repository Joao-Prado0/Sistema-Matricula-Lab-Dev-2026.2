package model;

import enums.TipoInscricao;

public class Inscricao {
    private TipoInscricao tipo;
    private Disciplina disciplina;
    private Aluno aluno;
    private boolean cancelada;

    public Inscricao() {
    }

    public Inscricao(Disciplina disciplina, Aluno aluno, TipoInscricao tipo) {
        this.disciplina = disciplina;
        this.aluno = aluno;
        this.tipo = tipo;
        this.cancelada = false;
    }

    public TipoInscricao getTipo() {
        return tipo;
    }

    public void setTipo(TipoInscricao tipo) {
        this.tipo = tipo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void cancelar() {
        this.cancelada = true;
    }
}
