package repository;

import java.util.ArrayList;
import java.util.List;

import interfaces.IArquivoRepository;
import model.Secretaria;

public class SecretariaRepository implements IArquivoRepository<Secretaria> {

    private String caminhoArquivo = "src/data/secretarias.txt";

    public SecretariaRepository() {
    }

    public SecretariaRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(Secretaria entidade) {
        String linha = entidade.getLogin() + "|" + entidade.getNome() + "|" + entidade.getSenha();
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<Secretaria> buscarTodos() {
        List<Secretaria> secretarias = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            Secretaria s = new Secretaria();
            s.setLogin(campos[0]);
            s.setNome(campos[1]);
            s.setSenha(campos[2]);
            secretarias.add(s);
        }
        return secretarias;
    }

    public Secretaria buscarPorLogin(String login) {
        for (Secretaria s : buscarTodos()) {
            if (s.getLogin().equals(login)) {
                return s;
            }
        }
        return null;
    }
}
