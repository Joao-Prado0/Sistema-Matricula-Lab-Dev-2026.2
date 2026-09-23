package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.StatusMatricula;
import enums.TipoInscricao;
import interfaces.IArquivoRepository;
import model.Aluno;
import model.Disciplina;
import model.Inscricao;
import model.Matricula;
import model.PeriodoMatricula;

public class MatriculaRepository implements IArquivoRepository<Matricula> {

    private String caminhoArquivo = "data/matriculas.txt";
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final PeriodoMatriculaRepository periodoRepository;

    public MatriculaRepository() {
        this.alunoRepository = new AlunoRepository();
        this.disciplinaRepository = new DisciplinaRepository();
        this.periodoRepository = new PeriodoMatriculaRepository();
    }

    public MatriculaRepository(String caminhoArquivo, AlunoRepository alunoRepository,
            DisciplinaRepository disciplinaRepository, PeriodoMatriculaRepository periodoRepository) {
        this.caminhoArquivo = caminhoArquivo;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.periodoRepository = periodoRepository;
    }

    private String serializar(Matricula m) {
        StringBuilder inscricoes = new StringBuilder();
        for (Inscricao i : m.getInscricoes()) {
            if (inscricoes.length() > 0) {
                inscricoes.append(";");
            }
            inscricoes.append(i.getDisciplina().getCodigo()).append(":")
                    .append(i.getTipo()).append(":").append(i.isCancelada());
        }
        return m.getAluno().getNumeroMatricula() + "|" + m.getSemestre() + "|"
                + m.getData().getTime() + "|" + m.getStatus() + "|" + inscricoes;
    }

    @Override
    public void salvar(Matricula entidade) {
        ArquivoUtils.adicionarLinha(caminhoArquivo, serializar(entidade));
    }

    public void salvarOuAtualizar(Matricula entidade) {
        List<String> linhas = ArquivoUtils.lerLinhas(caminhoArquivo);
        List<String> novasLinhas = new ArrayList<>();
        boolean atualizado = false;
        for (String linha : linhas) {
            String[] campos = linha.split("\\|", -1);
            if (campos[0].equals(entidade.getAluno().getNumeroMatricula())
                    && campos[1].equals(entidade.getSemestre())) {
                novasLinhas.add(serializar(entidade));
                atualizado = true;
            } else {
                novasLinhas.add(linha);
            }
        }
        if (!atualizado) {
            novasLinhas.add(serializar(entidade));
        }
        ArquivoUtils.regravarLinhas(caminhoArquivo, novasLinhas);
    }

    @Override
    public List<Matricula> buscarTodos() {
        List<Matricula> matriculas = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            matriculas.add(reconstruir(linha));
        }
        return matriculas;
    }

    private Matricula reconstruir(String linha) {
        String[] campos = linha.split("\\|", -1);
        Aluno aluno = alunoRepository.buscarPorMatricula(campos[0]);
        String semestre = campos[1];
        PeriodoMatricula periodo = periodoRepository.buscarPorSemestre(semestre);
        Matricula m = new Matricula(aluno, semestre, periodo);
        m.setData(new Date(Long.parseLong(campos[2])));
        m.setStatus(StatusMatricula.valueOf(campos[3]));
        if (campos.length > 4 && !campos[4].isEmpty()) {
            for (String token : campos[4].split(";")) {
                String[] partes = token.split(":", -1);
                Disciplina d = disciplinaRepository.buscarPorCodigo(partes[0]);
                Inscricao i = new Inscricao(d, aluno, TipoInscricao.valueOf(partes[1]));
                if (Boolean.parseBoolean(partes[2])) {
                    i.cancelar();
                } else if (d != null) {
                    d.adicionarInscricao(i);
                }
                m.getInscricoes().add(i);
            }
        }
        return m;
    }

    public List<Matricula> buscarPorAluno(String numeroMatricula) {
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula m : buscarTodos()) {
            if (m.getAluno() != null && m.getAluno().getNumeroMatricula().equals(numeroMatricula)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public Matricula buscarPorAlunoESemestre(String numeroMatricula, String semestre) {
        for (Matricula m : buscarPorAluno(numeroMatricula)) {
            if (m.getSemestre().equals(semestre)) {
                return m;
            }
        }
        return null;
    }
}
