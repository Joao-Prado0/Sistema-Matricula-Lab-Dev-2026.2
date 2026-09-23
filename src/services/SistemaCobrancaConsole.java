package services;

import interfaces.SistemaCobranca;
import model.Inscricao;
import model.Matricula;

public class SistemaCobrancaConsole implements SistemaCobranca {

    @Override
    public void notificarMatricula(Matricula m) {
        int creditos = 0;
        for (Inscricao i : m.getInscricoes()) {
            if (!i.isCancelada()) {
                creditos++;
            }
        }
        System.out.println("[Sistema de Cobranca] Aluno " + m.getAluno().getNumeroMatricula()
                + " | semestre " + m.getSemestre() + " | status " + m.getStatus()
                + " | disciplinas ativas: " + creditos);
    }
}
