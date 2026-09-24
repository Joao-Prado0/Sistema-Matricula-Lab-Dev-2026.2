package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.IArquivoRepository;
import model.PeriodoMatricula;

public class PeriodoMatriculaRepository implements IArquivoRepository<PeriodoMatricula> {

    private String caminhoArquivo = "src/data/periodos.txt";

    public PeriodoMatriculaRepository() {
    }

    public PeriodoMatriculaRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(PeriodoMatricula entidade) {
        String linha = entidade.getSemestre() + "|" + entidade.getDataInicio().getTime() + "|"
                + entidade.getDataFim().getTime() + "|" + entidade.isEncerrado();
        ArquivoUtils.adicionarLinha(caminhoArquivo, linha);
    }

    @Override
    public List<PeriodoMatricula> buscarTodos() {
        List<PeriodoMatricula> periodos = new ArrayList<>();
        for (String linha : ArquivoUtils.lerLinhas(caminhoArquivo)) {
            String[] campos = linha.split("\\|", -1);
            PeriodoMatricula p = new PeriodoMatricula(campos[0],
                    new Date(Long.parseLong(campos[1])), new Date(Long.parseLong(campos[2])));
            p.setEncerrado(Boolean.parseBoolean(campos[3]));
            periodos.add(p);
        }
        return periodos;
    }

    public PeriodoMatricula buscarPorSemestre(String semestre) {
        for (PeriodoMatricula p : buscarTodos()) {
            if (p.getSemestre().equals(semestre)) {
                return p;
            }
        }
        return null;
    }

    public void atualizarEncerrado(String semestre, boolean encerrado) {
        List<String> linhas = ArquivoUtils.lerLinhas(caminhoArquivo);
        List<String> novasLinhas = new ArrayList<>();
        for (String linha : linhas) {
            String[] campos = linha.split("\\|", -1);
            if (campos[0].equals(semestre)) {
                campos[3] = String.valueOf(encerrado);
                novasLinhas.add(String.join("|", campos));
            } else {
                novasLinhas.add(linha);
            }
        }
        ArquivoUtils.regravarLinhas(caminhoArquivo, novasLinhas);
    }
}
