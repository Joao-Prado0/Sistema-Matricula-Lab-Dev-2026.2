package repository;

import java.util.ArrayList;
import java.util.List;

import interfaces.IArquivoRepository;
import model.Aluno;
import model.Curso;

public class AlunoRepository implements IArquivoRepository<Aluno> {

    private String caminhoArquivo = "src/data/alunos.txt";
    private final CursoRepository cursoRepository;

    public AlunoRepository() {
        this.cursoRepository = new CursoRepository();
    }

    public AlunoRepository(String caminhoArquivo, CursoRepository cursoRepository) {
        this.caminhoArquivo = caminhoArquivo;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void salvar(Aluno entidade) {
        String cursoNome = entidade.getCurso() != null ? entidade.getCurso().getNome() : "";
        String linha = entidade.getNumeroMatricula() + "|" + entidade.getNome() + "|"
                + entidade.getLogin() + "|" + entidade.getSenha() + "|" + cursoNome;
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<Aluno> buscarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            Aluno a = new Aluno();
            a.setNumeroMatricula(campos[0]);
            a.setNome(campos[1]);
            a.setLogin(campos[2]);
            a.setSenha(campos[3]);
            if (campos.length > 4 && !campos[4].isEmpty()) {
                Curso curso = cursoRepository.buscarPorNome(campos[4]);
                a.setCurso(curso);
            }
            alunos.add(a);
        }
        return alunos;
    }

    public Aluno buscarPorMatricula(String numeroMatricula) {
        for (Aluno a : buscarTodos()) {
            if (a.getNumeroMatricula().equals(numeroMatricula)) {
                return a;
            }
        }
        return null;
    }

    public Aluno buscarPorLogin(String login) {
        for (Aluno a : buscarTodos()) {
            if (a.getLogin().equals(login)) {
                return a;
            }
        }
        return null;
    }
}
