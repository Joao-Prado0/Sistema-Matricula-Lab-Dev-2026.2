package repository;

import java.util.ArrayList;
import java.util.List;

import enums.StatusDisciplina;
import interfaces.IArquivoRepository;
import model.Curso;
import model.Disciplina;

public class DisciplinaRepository implements IArquivoRepository<Disciplina> {

    private String caminhoArquivo = "data/disciplinas.txt";
    private final CursoRepository cursoRepository;

    public DisciplinaRepository() {
        this.cursoRepository = new CursoRepository();
    }

    public DisciplinaRepository(String caminhoArquivo, CursoRepository cursoRepository) {
        this.caminhoArquivo = caminhoArquivo;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void salvar(Disciplina entidade) {
        String cursoNome = entidade.getCurso() != null ? entidade.getCurso().getNome() : "";
        String linha = entidade.getCodigo() + "|" + entidade.getNome() + "|"
                + entidade.getStatus() + "|" + cursoNome;
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<Disciplina> buscarTodos() {
        List<Disciplina> disciplinas = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            Disciplina d = new Disciplina(campos[0], campos[1]);
            d.setStatus(StatusDisciplina.valueOf(campos[2]));
            if (campos.length > 3 && !campos[3].isEmpty()) {
                Curso curso = cursoRepository.buscarPorNome(campos[3]);
                d.setCurso(curso);
            }
            disciplinas.add(d);
        }
        return disciplinas;
    }

    public Disciplina buscarPorCodigo(String codigo) {
        for (Disciplina d : buscarTodos()) {
            if (d.getCodigo().equals(codigo)) {
                return d;
            }
        }
        return null;
    }

    public void atualizarStatus(String codigo, StatusDisciplina status) {
        List<String> linhas = ArquivoUtils.lerLinhas(caminhoArquivo);
        List<String> novasLinhas = new ArrayList<>();
        for (String linha : linhas) {
            String[] campos = linha.split("\\|", -1);
            if (campos[0].equals(codigo)) {
                campos[2] = status.name();
                novasLinhas.add(String.join("|", campos));
            } else {
                novasLinhas.add(linha);
            }
        }
        ArquivoUtils.regravarLinhas(caminhoArquivo, novasLinhas);
    }
}
