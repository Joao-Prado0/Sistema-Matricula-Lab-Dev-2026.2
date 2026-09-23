package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import enums.TipoInscricao;
import interfaces.SistemaCobranca;
import model.Aluno;
import model.Disciplina;
import model.Professor;
import model.Secretaria;
import model.Usuario;
import repository.AlunoRepository;
import repository.CurriculoRepository;
import repository.CursoRepository;
import repository.DisciplinaRepository;
import repository.MatriculaRepository;
import repository.PeriodoMatriculaRepository;
import repository.ProfessorRepository;
import repository.SecretariaRepository;
import services.AutenticacaoService;
import services.DisciplinaService;
import services.MatriculaService;
import services.ProfessorService;
import services.SecretariaService;
import services.SistemaCobrancaConsole;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

    private final CursoRepository cursoRepository = new CursoRepository();
    private final DisciplinaRepository disciplinaRepository = new DisciplinaRepository("data/disciplinas.txt", cursoRepository);
    private final CurriculoRepository curriculoRepository = new CurriculoRepository("data/curriculos.txt", disciplinaRepository);
    private final AlunoRepository alunoRepository = new AlunoRepository("data/alunos.txt", cursoRepository);
    private final ProfessorRepository professorRepository = new ProfessorRepository();
    private final SecretariaRepository secretariaRepository = new SecretariaRepository();
    private final PeriodoMatriculaRepository periodoRepository = new PeriodoMatriculaRepository();
    private final MatriculaRepository matriculaRepository = new MatriculaRepository(
            "data/matriculas.txt", alunoRepository, disciplinaRepository, periodoRepository);

    private final SistemaCobranca sistemaCobranca = new SistemaCobrancaConsole();

    private final AutenticacaoService autenticacaoService =
            new AutenticacaoService(alunoRepository, professorRepository, secretariaRepository);
    private final SecretariaService secretariaService = new SecretariaService(
            cursoRepository, disciplinaRepository, curriculoRepository, alunoRepository, professorRepository, periodoRepository);
    private final MatriculaService matriculaService = new MatriculaService(
            matriculaRepository, alunoRepository, disciplinaRepository, periodoRepository, sistemaCobranca);
    private final DisciplinaService disciplinaService = new DisciplinaService(disciplinaRepository, matriculaRepository);
    private final ProfessorService professorService = new ProfessorService(disciplinaRepository, matriculaRepository);

    public void iniciar() {
        System.out.println("=== Sistema de Matriculas ===");
        garantirSecretariaPadrao();
        boolean continuar = true;
        while (continuar) {
            System.out.print("\nLogin: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            Usuario usuario = autenticacaoService.autenticarUsuario(login, senha);
            if (usuario == null) {
                System.out.println("Credenciais invalidas.");
                continue;
            }
            if (usuario instanceof Secretaria) {
                menuSecretaria();
            } else if (usuario instanceof Aluno) {
                menuAluno((Aluno) usuario);
            } else if (usuario instanceof Professor) {
                menuProfessor((Professor) usuario);
            }
            System.out.print("\nDeseja fazer outro login? (s/n): ");
            continuar = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
        System.out.println("Encerrando sistema.");
    }

    private void garantirSecretariaPadrao() {
        if (secretariaRepository.buscarPorLogin("secretaria") == null) {
            Secretaria s = new Secretaria();
            s.setNome("Secretaria Academica");
            s.setLogin("secretaria");
            s.setSenha("admin");
            secretariaRepository.salvar(s);
            System.out.println("Usuario padrao criado -> login: secretaria | senha: admin");
        }
    }

    private void menuSecretaria() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n-- Menu Secretaria --");
            System.out.println("1) Cadastrar curso");
            System.out.println("2) Cadastrar disciplina");
            System.out.println("3) Gerar curriculo do semestre");
            System.out.println("4) Cadastrar aluno");
            System.out.println("5) Cadastrar professor");
            System.out.println("6) Abrir periodo de matricula");
            System.out.println("7) Encerrar periodo e avaliar ativacao de turmas");
            System.out.println("0) Voltar");
            System.out.print("Opcao: ");
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1":
                        System.out.print("Nome do curso: ");
                        String nomeCurso = scanner.nextLine();
                        System.out.print("Numero de creditos: ");
                        int creditos = Integer.parseInt(scanner.nextLine());
                        secretariaService.cadastrarCurso(nomeCurso, creditos);
                        System.out.println("Curso cadastrado.");
                        break;
                    case "2":
                        System.out.print("Codigo da disciplina: ");
                        String codigo = scanner.nextLine();
                        System.out.print("Nome da disciplina: ");
                        String nomeDisciplina = scanner.nextLine();
                        System.out.print("Nome do curso vinculado: ");
                        String cursoVinculado = scanner.nextLine();
                        secretariaService.cadastrarDisciplina(codigo, nomeDisciplina, cursoVinculado);
                        System.out.println("Disciplina cadastrada.");
                        break;
                    case "3":
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestreCurriculo = scanner.nextLine();
                        System.out.print("Codigos das disciplinas (separados por virgula): ");
                        List<String> codigos = Arrays.asList(scanner.nextLine().split(","));
                        secretariaService.gerarCurriculo(semestreCurriculo, codigos);
                        System.out.println("Curriculo gerado.");
                        break;
                    case "4":
                        System.out.print("Numero de matricula: ");
                        String numeroMatricula = scanner.nextLine();
                        System.out.print("Nome do aluno: ");
                        String nomeAluno = scanner.nextLine();
                        System.out.print("Login: ");
                        String loginAluno = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senhaAluno = scanner.nextLine();
                        System.out.print("Curso do aluno: ");
                        String cursoAluno = scanner.nextLine();
                        secretariaService.cadastrarAluno(numeroMatricula, nomeAluno, loginAluno, senhaAluno, cursoAluno);
                        System.out.println("Aluno cadastrado.");
                        break;
                    case "5":
                        System.out.print("Codigo funcional: ");
                        String codigoFuncional = scanner.nextLine();
                        System.out.print("Nome do professor: ");
                        String nomeProfessor = scanner.nextLine();
                        System.out.print("Login: ");
                        String loginProfessor = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senhaProfessor = scanner.nextLine();
                        secretariaService.cadastrarProfessor(codigoFuncional, nomeProfessor, loginProfessor, senhaProfessor);
                        System.out.println("Professor cadastrado.");
                        break;
                    case "6":
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestrePeriodo = scanner.nextLine();
                        Date inicio = lerData("Data de inicio (yyyy-MM-dd): ");
                        Date fim = lerData("Data de fim (yyyy-MM-dd): ");
                        secretariaService.abrirPeriodoMatricula(semestrePeriodo, inicio, fim);
                        System.out.println("Periodo de matricula aberto.");
                        break;
                    case "7":
                        System.out.print("Semestre a encerrar: ");
                        String semestreEncerrar = scanner.nextLine();
                        periodoRepository.atualizarEncerrado(semestreEncerrar, true);
                        disciplinaService.avaliarAtivacaoTurmas();
                        System.out.println("Periodo encerrado e disciplinas avaliadas.");
                        break;
                    case "0":
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void menuAluno(Aluno aluno) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n-- Menu Aluno (" + aluno.getNumeroMatricula() + ") --");
            System.out.println("1) Matricular em disciplina");
            System.out.println("2) Cancelar inscricao em disciplina");
            System.out.println("3) Confirmar matricula do semestre");
            System.out.println("4) Cancelar matricula do semestre");
            System.out.println("5) Consultar grade de matricula");
            System.out.println("0) Voltar");
            System.out.print("Opcao: ");
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1": {
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestre = scanner.nextLine();
                        System.out.print("Codigo da disciplina: ");
                        String codigo = scanner.nextLine();
                        System.out.print("Tipo (OBRIGATORIA/OPTATIVA): ");
                        TipoInscricao tipo = TipoInscricao.valueOf(scanner.nextLine().trim().toUpperCase());
                        matriculaService.realizarMatricula(aluno.getNumeroMatricula(), semestre, codigo, tipo);
                        System.out.println("Inscricao realizada.");
                        break;
                    }
                    case "2": {
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestre = scanner.nextLine();
                        System.out.print("Codigo da disciplina: ");
                        String codigo = scanner.nextLine();
                        matriculaService.cancelarInscricao(aluno.getNumeroMatricula(), semestre, codigo);
                        System.out.println("Inscricao cancelada.");
                        break;
                    }
                    case "3": {
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestre = scanner.nextLine();
                        matriculaService.confirmarMatricula(aluno.getNumeroMatricula(), semestre);
                        System.out.println("Matricula confirmada.");
                        break;
                    }
                    case "4": {
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestre = scanner.nextLine();
                        matriculaService.cancelarMatricula(aluno.getNumeroMatricula(), semestre);
                        System.out.println("Matricula cancelada.");
                        break;
                    }
                    case "5": {
                        System.out.print("Semestre (ex: 2026/2): ");
                        String semestre = scanner.nextLine();
                        var matricula = matriculaRepository.buscarPorAlunoESemestre(aluno.getNumeroMatricula(), semestre);
                        if (matricula == null) {
                            System.out.println("Nenhuma matricula encontrada.");
                        } else {
                            System.out.println("Status: " + matricula.getStatus());
                            for (var i : matricula.getInscricoes()) {
                                Disciplina d = i.getDisciplina();
                                System.out.println(" - " + (d != null ? d.getCodigo() + " " + d.getNome() : "?")
                                        + " | " + i.getTipo() + " | cancelada=" + i.isCancelada());
                            }
                        }
                        break;
                    }
                    case "0":
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void menuProfessor(Professor professor) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n-- Menu Professor (" + professor.getCodigoFuncional() + ") --");
            System.out.println("1) Consultar alunos matriculados em disciplina");
            System.out.println("0) Voltar");
            System.out.print("Opcao: ");
            String opcao = scanner.nextLine().trim();
            try {
                switch (opcao) {
                    case "1": {
                        System.out.print("Codigo da disciplina: ");
                        String codigo = scanner.nextLine();
                        List<Aluno> alunos = professorService.consultarAlunosNaDisciplina(codigo);
                        if (alunos.isEmpty()) {
                            System.out.println("Nenhum aluno matriculado.");
                        } else {
                            for (Aluno a : alunos) {
                                System.out.println(" - " + a.getNumeroMatricula() + " " + a.getNome());
                            }
                        }
                        break;
                    }
                    case "0":
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private Date lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            try {
                return formatoData.parse(texto);
            } catch (ParseException e) {
                System.out.println("Data invalida, use o formato yyyy-MM-dd.");
            }
        }
    }
}
