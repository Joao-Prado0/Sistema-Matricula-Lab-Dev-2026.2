package repository;

import java.util.ArrayList;
import java.util.List;

import interfaces.IArquivoRepository;
import model.Curriculo;
import model.Disciplina;

public class CurriculoRepository implements IArquivoRepository<Curriculo> {

    private String caminhoArquivo = "src/data/curriculos.txt";
    private final DisciplinaRepository disciplinaRepository;

    public CurriculoRepository() {
        this.disciplinaRepository = new DisciplinaRepository();
    }

    public CurriculoRepository(String caminhoArquivo, DisciplinaRepository disciplinaRepository) {
        this.caminhoArquivo = caminhoArquivo;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void salvar(Curriculo entidade) {
        StringBuilder codigos = new StringBuilder();
        for (Disciplina d : entidade.getDisciplinas()) {
            if (codigos.length() > 0) {
                codigos.append(",");
            }
            codigos.append(d.getCodigo());
        }
        String linha = entidade.getSemestre() + "|" + codigos;
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<Curriculo> buscarTodos() {
        List<Curriculo> curriculos = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            Curriculo c = new Curriculo(campos[0]);
            if (campos.length > 1 && !campos[1].isEmpty()) {
                for (String codigo : campos[1].split(",")) {
                    Disciplina d = disciplinaRepository.buscarPorCodigo(codigo);
                    if (d != null) {
                        c.adicionarDisciplina(d);
                    }
                }
            }
            curriculos.add(c);
        }
        return curriculos;
    }

    public Curriculo buscarPorSemestre(String semestre) {
        for (Curriculo c : buscarTodos()) {
            if (c.getSemestre().equals(semestre)) {
                return c;
            }
        }
        return null;
    }
}
