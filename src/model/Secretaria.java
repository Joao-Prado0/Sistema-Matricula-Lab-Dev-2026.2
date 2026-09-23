package model;

import java.util.Date;

public class Secretaria extends Usuario {

    public Curriculo gerarCurriculo(String semestre) {
        return new Curriculo(semestre);
    }

    public void cadastrarDisciplina(Disciplina d) {
        // persistencia delegada a DisciplinaRepository via SecretariaService
    }

    public void cadastrarAluno(Aluno a) {
        // persistencia delegada a AlunoRepository via SecretariaService
    }

    public void cadastrarProfessor(Professor p) {
        // persistencia delegada a ProfessorRepository via SecretariaService
    }

    public PeriodoMatricula abrirPeriodoMatricula(Date inicio, Date fim) {
        return new PeriodoMatricula(null, inicio, fim);
    }
}
