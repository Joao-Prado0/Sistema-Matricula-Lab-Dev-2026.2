package model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int numeroCreditos;
    private final List<Disciplina> disciplinas = new ArrayList<>();

    public Curso() {
    }

    public Curso(String nome, int numeroCreditos) {
        this.nome = nome;
        this.numeroCreditos = numeroCreditos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroCreditos() {
        return numeroCreditos;
    }

    public void setNumeroCreditos(int numeroCreditos) {
        this.numeroCreditos = numeroCreditos;
    }

    public void adicionarDisciplina(Disciplina d) {
        d.setCurso(this);
        disciplinas.add(d);
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }
}
