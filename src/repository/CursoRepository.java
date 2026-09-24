package repository;

import java.util.ArrayList;
import java.util.List;

import interfaces.IArquivoRepository;
import model.Curso;

public class CursoRepository implements IArquivoRepository<Curso> {

    private String caminhoArquivo = "src/data/cursos.txt";

    public CursoRepository() {
    }

    public CursoRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(Curso entidade) {
        String linha = entidade.getNome() + "|" + entidade.getNumeroCreditos();
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<Curso> buscarTodos() {
        List<Curso> cursos = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            cursos.add(new Curso(campos[0], Integer.parseInt(campos[1])));
        }
        return cursos;
    }

    public Curso buscarPorNome(String nome) {
        for (Curso c : buscarTodos()) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }
}
