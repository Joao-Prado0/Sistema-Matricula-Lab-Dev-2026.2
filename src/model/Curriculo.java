package model;

import java.util.ArrayList;
import java.util.List;

public class Curriculo {
    private String semestre;
    private final List<Disciplina> disciplinas = new ArrayList<>();

    public Curriculo() {
    }

    public Curriculo(String semestre) {
        this.semestre = semestre;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public void adicionarDisciplina(Disciplina d) {
        disciplinas.add(d);
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}
