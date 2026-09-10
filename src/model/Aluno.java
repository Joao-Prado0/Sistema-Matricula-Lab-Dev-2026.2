package model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {

    private String numeroMatricula;

    // setters getters
    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    // metodos
    public void inscreverEm(Disciplina d, TipoInscricao tipo) {
    }

    public void cancelarInscricao(Disciplina d){}

    public void cancelarMatricula(Matricula m){}

    public List<Disciplina> consultarDisciplina(){
        return new ArrayList<>();
    }

}
