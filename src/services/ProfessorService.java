package services;

import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Disciplina;
import model.Inscricao;
import model.Matricula;
import repository.DisciplinaRepository;
import repository.MatriculaRepository;

public class ProfessorService {

    private final DisciplinaRepository disciplinaRepository;
    private final MatriculaRepository matriculaRepository;

    public ProfessorService(DisciplinaRepository disciplinaRepository, MatriculaRepository matriculaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public List<Aluno> consultarAlunosNaDisciplina(String codigoDisciplina) {
        Disciplina disciplina = disciplinaRepository.buscarPorCodigo(codigoDisciplina);
        List<Aluno> alunos = new ArrayList<>();
        if (disciplina == null) {
            return alunos;
        }
        for (Matricula m : matriculaRepository.buscarTodos()) {
            for (Inscricao i : m.getInscricoes()) {
                if (!i.isCancelada() && i.getDisciplina() != null
                        && i.getDisciplina().getCodigo().equals(codigoDisciplina)) {
                    alunos.add(i.getAluno());
                }
            }
        }
        return alunos;
    }
}
