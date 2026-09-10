package model;

import java.util.List;

public class Professor extends Usuario {
    private String codigoFuncional;

    public String getCodigoFuncional() {
        return codigoFuncional;
    }

    public void setCodigoFuncional(String codigoFuncional) {
        this.codigoFuncional = codigoFuncional;
    }

    public List<Aluno> consultarAlunos(Disciplina d) {
        return null;
    }
}