package repository;

import java.util.ArrayList;
import java.util.List;

import interfaces.IArquivoRepository;
import model.Professor;

public class ProfessorRepository implements IArquivoRepository<Professor> {

    private String caminhoArquivo = "src/data/professores.txt";

    public ProfessorRepository() {
    }

    public ProfessorRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(Professor entidade) {
        String linha = entidade.getCodigoFuncional() + "|" + entidade.getNome() + "|"
                + entidade.getLogin() + "|" + entidade.getSenha();
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<Professor> buscarTodos() {
        List<Professor> professores = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            Professor p = new Professor();
            p.setCodigoFuncional(campos[0]);
            p.setNome(campos[1]);
            p.setLogin(campos[2]);
            p.setSenha(campos[3]);
            professores.add(p);
        }
        return professores;
    }

    public Professor buscarPorLogin(String login) {
        for (Professor p : buscarTodos()) {
            if (p.getLogin().equals(login)) {
                return p;
            }
        }
        return null;
    }
}
