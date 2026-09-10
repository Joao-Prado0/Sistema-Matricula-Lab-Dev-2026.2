package model;

import java.util.List;

import enums.StatusDisciplina;

public class Disciplina {
    private String codigo;
    private String nome;
    private StatusDisciplina status;
    public static final int MIN_ALUNOS = 3; // Valor padrão configurável conforme necessidade
    public static final int MAX_ALUNOS = 60;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusDisciplina getStatus() {
        return status;
    }

    public void setStatus(StatusDisciplina status) {
        this.status = status;
    }

    public int getTotalInscritos() {
        return 0;
    }

    public boolean estaLotada() {
        return false;
    }

    public void verificarAtivacao() {
    }

    public List<Aluno> listarAlunosMatriculados() {
        return null;
    }
}