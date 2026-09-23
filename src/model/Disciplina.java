package model;

import java.util.ArrayList;
import java.util.List;

import enums.StatusDisciplina;

public class Disciplina {
    private String codigo;
    private String nome;
    private StatusDisciplina status;
    private Curso curso;
    private final List<Inscricao> inscricoes = new ArrayList<>();

    public static final int MIN_ALUNOS = 3;
    public static final int MAX_ALUNOS = 60;

    public Disciplina() {
        this.status = StatusDisciplina.PLANEJADA;
    }

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.status = StatusDisciplina.PLANEJADA;
    }

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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void adicionarInscricao(Inscricao i) {
        inscricoes.add(i);
    }

    public void removerInscricao(Inscricao i) {
        inscricoes.remove(i);
    }

    public int getTotalInscritos() {
        int total = 0;
        for (Inscricao i : inscricoes) {
            if (!i.isCancelada()) {
                total++;
            }
        }
        return total;
    }

    public boolean estaLotada() {
        return getTotalInscritos() >= MAX_ALUNOS;
    }

    public void verificarAtivacao() {
        status = getTotalInscritos() >= MIN_ALUNOS ? StatusDisciplina.ATIVA : StatusDisciplina.CANCELADA;
    }

    public List<Aluno> listarAlunosMatriculados() {
        List<Aluno> alunos = new ArrayList<>();
        for (Inscricao i : inscricoes) {
            if (!i.isCancelada()) {
                alunos.add(i.getAluno());
            }
        }
        return alunos;
    }
}
