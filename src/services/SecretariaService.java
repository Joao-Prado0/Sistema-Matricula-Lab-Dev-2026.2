package services;

import java.util.Date;
import java.util.List;

import model.Aluno;
import model.Curriculo;
import model.Curso;
import model.Disciplina;
import model.PeriodoMatricula;
import model.Professor;
import repository.AlunoRepository;
import repository.CurriculoRepository;
import repository.CursoRepository;
import repository.DisciplinaRepository;
import repository.PeriodoMatriculaRepository;
import repository.ProfessorRepository;

public class SecretariaService {

    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final CurriculoRepository curriculoRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final PeriodoMatriculaRepository periodoRepository;

    public SecretariaService(CursoRepository cursoRepository, DisciplinaRepository disciplinaRepository,
            CurriculoRepository curriculoRepository, AlunoRepository alunoRepository,
            ProfessorRepository professorRepository, PeriodoMatriculaRepository periodoRepository) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.curriculoRepository = curriculoRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.periodoRepository = periodoRepository;
    }

    public Curso cadastrarCurso(String nome, int numeroCreditos) {
        Curso curso = new Curso(nome, numeroCreditos);
        cursoRepository.salvar(curso);
        return curso;
    }

    public Disciplina cadastrarDisciplina(String codigo, String nome, String nomeCurso) {
        Curso curso = cursoRepository.buscarPorNome(nomeCurso);
        Disciplina disciplina = new Disciplina(codigo, nome);
        if (curso != null) {
            disciplina.setCurso(curso);
        }
        disciplinaRepository.salvar(disciplina);
        return disciplina;
    }

    public Curriculo gerarCurriculo(String semestre, List<String> codigosDisciplinas) {
        Curriculo curriculo = new Curriculo(semestre);
        for (String codigo : codigosDisciplinas) {
            Disciplina d = disciplinaRepository.buscarPorCodigo(codigo);
            if (d != null) {
                curriculo.adicionarDisciplina(d);
            }
        }
        curriculoRepository.salvar(curriculo);
        return curriculo;
    }

    public Aluno cadastrarAluno(String numeroMatricula, String nome, String login, String senha, String nomeCurso) {
        Aluno aluno = new Aluno();
        aluno.setNumeroMatricula(numeroMatricula);
        aluno.setNome(nome);
        aluno.setLogin(login);
        aluno.setSenha(senha);
        Curso curso = cursoRepository.buscarPorNome(nomeCurso);
        aluno.setCurso(curso);
        alunoRepository.salvar(aluno);
        return aluno;
    }

    public Professor cadastrarProfessor(String codigoFuncional, String nome, String login, String senha) {
        Professor professor = new Professor();
        professor.setCodigoFuncional(codigoFuncional);
        professor.setNome(nome);
        professor.setLogin(login);
        professor.setSenha(senha);
        professorRepository.salvar(professor);
        return professor;
    }

    public PeriodoMatricula abrirPeriodoMatricula(String semestre, Date inicio, Date fim) {
        PeriodoMatricula periodo = new PeriodoMatricula(semestre, inicio, fim);
        periodoRepository.salvar(periodo);
        return periodo;
    }
}
