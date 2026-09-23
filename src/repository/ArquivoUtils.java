package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ArquivoUtils {

    static List<String> lerLinhas(String caminhoArquivo) {
        List<String> linhas = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return linhas;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo " + caminhoArquivo, e);
        }
        return linhas;
    }

    static void adicionarLinha(String caminhoArquivo, String linha) {
        File arquivo = new File(caminhoArquivo);
        File pasta = arquivo.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
        try (FileWriter writer = new FileWriter(arquivo, true)) {
            writer.write(linha);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever no arquivo " + caminhoArquivo, e);
        }
    }

    static void regravarLinhas(String caminhoArquivo, List<String> linhas) {
        File arquivo = new File(caminhoArquivo);
        File pasta = arquivo.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
        try (FileWriter writer = new FileWriter(arquivo, false)) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao regravar arquivo " + caminhoArquivo, e);
        }
    }
}
