package model;

import java.util.ArrayList;
import java.util.List;

import enums.TipoInscricao;

public class Aluno extends Usuario {

    private String numeroMatricula;
    private Curso curso;
    private final List<Matricula> matriculas = new ArrayList<>();
    private Matricula matriculaAtual;

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void adicionarMatricula(Matricula m) {
        matriculas.add(m);
        this.matriculaAtual = m;
    }

    public Matricula getMatriculaAtual() {
        return matriculaAtual;
    }

    public void setMatriculaAtual(Matricula matriculaAtual) {
        this.matriculaAtual = matriculaAtual;
    }

    public void inscreverEm(Disciplina d, TipoInscricao tipo) {
        if (matriculaAtual == null) {
            throw new IllegalStateException("Aluno nao possui matricula ativa no periodo.");
        }
        matriculaAtual.adicionarInscricao(d, tipo);
    }

    public void cancelarInscricao(Disciplina d) {
        if (matriculaAtual == null) {
            return;
        }
        matriculaAtual.removerInscricao(d);
    }

    public void cancelarMatricula(Matricula m) {
        if (m != null) {
            m.cancelar();
        }
    }

    public List<Disciplina> consultarDisciplina() {
        List<Disciplina> disciplinas = new ArrayList<>();
        if (matriculaAtual == null) {
            return disciplinas;
        }
        for (Inscricao i : matriculaAtual.getInscricoes()) {
            if (!i.isCancelada()) {
                disciplinas.add(i.getDisciplina());
            }
        }
        return disciplinas;
    }
}
