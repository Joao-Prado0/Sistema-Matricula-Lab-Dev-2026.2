package services;

import java.util.HashMap;
import java.util.Map;

import enums.TipoInscricao;
import interfaces.SistemaCobranca;
import model.Aluno;
import model.Disciplina;
import model.Inscricao;
import model.Matricula;
import model.PeriodoMatricula;
import repository.AlunoRepository;
import repository.DisciplinaRepository;
import repository.MatriculaRepository;
import repository.PeriodoMatriculaRepository;

public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final PeriodoMatriculaRepository periodoRepository;
    private final SistemaCobranca sistemaCobranca;

    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository,
            DisciplinaRepository disciplinaRepository, PeriodoMatriculaRepository periodoRepository,
            SistemaCobranca sistemaCobranca) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.periodoRepository = periodoRepository;
        this.sistemaCobranca = sistemaCobranca;
    }

    private int totalInscritosAtivos(String codigoDisciplina) {
        Map<String, Integer> totais = new HashMap<>();
        for (Matricula m : matriculaRepository.buscarTodos()) {
            for (Inscricao i : m.getInscricoes()) {
                if (!i.isCancelada() && i.getDisciplina() != null) {
                    totais.merge(i.getDisciplina().getCodigo(), 1, Integer::sum);
                }
            }
        }
        return totais.getOrDefault(codigoDisciplina, 0);
    }

    public Matricula realizarMatricula(String numeroMatricula, String semestre, String codigoDisciplina, TipoInscricao tipo) {
        Aluno aluno = alunoRepository.buscarPorMatricula(numeroMatricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno nao encontrado: " + numeroMatricula);
        }
        Disciplina disciplina = disciplinaRepository.buscarPorCodigo(codigoDisciplina);
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina nao encontrada: " + codigoDisciplina);
        }
        PeriodoMatricula periodo = periodoRepository.buscarPorSemestre(semestre);
        if (periodo == null || !periodo.estaAberto()) {
            throw new IllegalStateException("Periodo de matricula fechado para o semestre " + semestre);
        }
        if (totalInscritosAtivos(codigoDisciplina) >= Disciplina.MAX_ALUNOS) {
            throw new IllegalStateException("Disciplina " + codigoDisciplina + " esta lotada.");
        }

        Matricula matricula = matriculaRepository.buscarPorAlunoESemestre(numeroMatricula, semestre);
        if (matricula == null) {
            matricula = new Matricula(aluno, semestre, periodo);
        }
        matricula.setSistemaCobranca(sistemaCobranca);
        matricula.adicionarInscricao(disciplina, tipo);
        matriculaRepository.salvarOuAtualizar(matricula);
        return matricula;
    }

    public void cancelarInscricao(String numeroMatricula, String semestre, String codigoDisciplina) {
        Matricula matricula = matriculaRepository.buscarPorAlunoESemestre(numeroMatricula, semestre);
        if (matricula == null) {
            throw new IllegalArgumentException("Matricula nao encontrada para o aluno/semestre informados.");
        }
        Disciplina disciplina = disciplinaRepository.buscarPorCodigo(codigoDisciplina);
        matricula.setSistemaCobranca(sistemaCobranca);
        matricula.removerInscricao(disciplina);
        matriculaRepository.salvarOuAtualizar(matricula);
    }

    public void confirmarMatricula(String numeroMatricula, String semestre) {
        Matricula matricula = matriculaRepository.buscarPorAlunoESemestre(numeroMatricula, semestre);
        if (matricula == null) {
            throw new IllegalArgumentException("Matricula nao encontrada para o aluno/semestre informados.");
        }
        matricula.setSistemaCobranca(sistemaCobranca);
        matricula.confirmar();
        matriculaRepository.salvarOuAtualizar(matricula);
    }

    public void cancelarMatricula(String numeroMatricula, String semestre) {
        Matricula matricula = matriculaRepository.buscarPorAlunoESemestre(numeroMatricula, semestre);
        if (matricula == null) {
            throw new IllegalArgumentException("Matricula nao encontrada para o aluno/semestre informados.");
        }
        matricula.setSistemaCobranca(sistemaCobranca);
        matricula.cancelar();
        matriculaRepository.salvarOuAtualizar(matricula);
    }
}
