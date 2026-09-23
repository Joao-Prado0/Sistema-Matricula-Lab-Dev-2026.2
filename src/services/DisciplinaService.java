package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.StatusDisciplina;
import model.Disciplina;
import model.Inscricao;
import model.Matricula;
import repository.DisciplinaRepository;
import repository.MatriculaRepository;

public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final MatriculaRepository matriculaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, MatriculaRepository matriculaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public void avaliarAtivacaoTurmas() {
        Map<String, Integer> totalPorDisciplina = new HashMap<>();
        for (Matricula m : matriculaRepository.buscarTodos()) {
            for (Inscricao i : m.getInscricoes()) {
                if (!i.isCancelada() && i.getDisciplina() != null) {
                    String codigo = i.getDisciplina().getCodigo();
                    totalPorDisciplina.merge(codigo, 1, Integer::sum);
                }
            }
        }
        List<Disciplina> disciplinas = disciplinaRepository.buscarTodos();
        for (Disciplina d : disciplinas) {
            int total = totalPorDisciplina.getOrDefault(d.getCodigo(), 0);
            StatusDisciplina status = total >= Disciplina.MIN_ALUNOS ? StatusDisciplina.ATIVA : StatusDisciplina.CANCELADA;
            disciplinaRepository.atualizarStatus(d.getCodigo(), status);
        }
    }
}
