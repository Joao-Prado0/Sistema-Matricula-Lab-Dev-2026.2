package services;

import model.Aluno;
import model.Professor;
import model.Secretaria;
import model.Usuario;
import repository.AlunoRepository;
import repository.ProfessorRepository;
import repository.SecretariaRepository;

public class AutenticacaoService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final SecretariaRepository secretariaRepository;

    public AutenticacaoService(AlunoRepository alunoRepository, ProfessorRepository professorRepository,
            SecretariaRepository secretariaRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.secretariaRepository = secretariaRepository;
    }

    public Usuario autenticarUsuario(String login, String senha) {
        Aluno aluno = alunoRepository.buscarPorLogin(login);
        if (aluno != null && aluno.autenticar(login, senha)) {
            return aluno;
        }
        Professor professor = professorRepository.buscarPorLogin(login);
        if (professor != null && professor.autenticar(login, senha)) {
            return professor;
        }
        Secretaria secretaria = secretariaRepository.buscarPorLogin(login);
        if (secretaria != null && secretaria.autenticar(login, senha)) {
            return secretaria;
        }
        return null;
    }
}
