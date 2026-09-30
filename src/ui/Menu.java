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
import model.Curriculo;
import model.Disciplina;
import model.PeriodoMatricula;
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
    private final DisciplinaRepository disciplinaRepository = new DisciplinaRepository("src/data/disciplinas.txt", cursoRepository);
    private final CurriculoRepository curriculoRepository = new CurriculoRepository("src/data/curriculos.txt", disciplinaRepository);
    private final AlunoRepository alunoRepository = new AlunoRepository("src/data/alunos.txt", cursoRepository);
    private final ProfessorRepository professorRepository = new ProfessorRepository();
    private final SecretariaRepository secretariaRepository = new SecretariaRepository();
    private final PeriodoMatriculaRepository periodoRepository = new PeriodoMatriculaRepository();
    private final MatriculaRepository matriculaRepository = new MatriculaRepository(
            "src/data/matriculas.txt", alunoRepository, disciplinaRepository, periodoRepository);

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
                        String semestreCurriculo = solicitarSemestre(false, false);
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
                        String semestrePeriodo = solicitarSemestre(false, false);
                        Date inicio = lerData("Data de inicio (yyyy-MM-dd): ");
                        Date fim = lerData("Data de fim (yyyy-MM-dd): ");
                        secretariaService.abrirPeriodoMatricula(semestrePeriodo, inicio, fim);
                        System.out.println("Periodo de matricula aberto.");
                        break;
                    case "7":
                        String semestreEncerrar = solicitarSemestre(true, false);
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
                        String semestre = selecionarSemestreParaMatricula();
                        String codigo = selecionarDisciplinaDoSemestre(semestre);
                        TipoInscricao tipo = selecionarTipoInscricao();
                        matriculaService.realizarMatricula(aluno.getNumeroMatricula(), semestre, codigo, tipo);
                        System.out.println("Inscricao realizada.");
                        break;
                    }
                    case "2": {
                        String semestre = solicitarSemestre(true, false);
                        System.out.print("Codigo da disciplina: ");
                        String codigo = scanner.nextLine();
                        matriculaService.cancelarInscricao(aluno.getNumeroMatricula(), semestre, codigo);
                        System.out.println("Inscricao cancelada.");
                        break;
                    }
                    case "3": {
                        String semestre = solicitarSemestre(true, false);
                        matriculaService.confirmarMatricula(aluno.getNumeroMatricula(), semestre);
                        System.out.println("Matricula confirmada.");
                        break;
                    }
                    case "4": {
                        String semestre = solicitarSemestre(true, false);
                        matriculaService.cancelarMatricula(aluno.getNumeroMatricula(), semestre);
                        System.out.println("Matricula cancelada.");
                        break;
                    }
                    case "5": {
                        String semestre = solicitarSemestre(true, false);
                        var matricula = matriculaRepository.buscarPorAlunoESemestre(aluno.getNumeroMatricula(), semestre);
                        if (matricula == null) {
                            System.out.println("Matricula vazia.");
                        } else {
                            System.out.println("Status: " + matricula.getStatus());
                            boolean possuiDisciplinasAtivas = false;
                            for (var i : matricula.getInscricoes()) {
                                if (i.isCancelada()) {
                                    continue;
                                }
                                possuiDisciplinasAtivas = true;
                                Disciplina d = i.getDisciplina();
                                System.out.println(" - " + (d != null ? d.getCodigo() + " " + d.getNome() : "?")
                                        + " | " + i.getTipo());
                            }
                            if (!possuiDisciplinasAtivas) {
                                System.out.println("Matricula vazia.");
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

    private String selecionarSemestreParaMatricula() {
        return solicitarSemestre(true, true);
    }

    private String solicitarSemestre(boolean exigirSemestreCadastrado, boolean apenasPeriodosAbertos) {
        while (true) {
            List<PeriodoMatricula> periodos = periodoRepository.buscarTodos();
            System.out.println("\nSemestres disponiveis:");
            boolean existeOpcao = false;
            for (PeriodoMatricula periodo : periodos) {
                if (!apenasPeriodosAbertos || periodo.estaAberto()) {
                    System.out.println(" - " + periodo.getSemestre()
                            + (periodo.estaAberto() ? " (aberto)" : " (fechado)"));
                    existeOpcao = true;
                }
            }
            if (!existeOpcao && exigirSemestreCadastrado) {
                throw new IllegalStateException("Nao ha semestres disponiveis para selecao.");
            }

            System.out.print(exigirSemestreCadastrado ? "Semestre: " : "Semestre (novo ou listado): ");
            String semestre = scanner.nextLine().trim();
            if (!exigirSemestreCadastrado && !semestre.isEmpty()) {
                return semestre;
            }
            PeriodoMatricula periodo = periodoRepository.buscarPorSemestre(semestre);
            if (periodo != null && (!apenasPeriodosAbertos || periodo.estaAberto())) {
                return semestre;
            }
            System.out.println("Semestre invalido para esta operacao. Tente novamente.");
        }
    }

    private TipoInscricao selecionarTipoInscricao() {
        while (true) {
            System.out.println("Tipo de inscricao:");
            System.out.println("1) Obrigatoria");
            System.out.println("2) Optativa");
            System.out.print("Opcao: ");
            String opcao = scanner.nextLine().trim();
            if (opcao.equals("1")) {
                return TipoInscricao.OBRIGATORIA;
            }
            if (opcao.equals("2")) {
                return TipoInscricao.OPTATIVA;
            }
            System.out.println("Opcao invalida. Informe 1 para obrigatoria ou 2 para optativa.");
        }
    }

    private String selecionarDisciplinaDoSemestre(String semestre) {
        while (true) {
            Curriculo curriculo = curriculoRepository.buscarPorSemestre(semestre);
            if (curriculo == null || curriculo.getDisciplinas().isEmpty()) {
                throw new IllegalStateException("Nao ha disciplinas cadastradas para o semestre " + semestre + ".");
            }

            System.out.println("\nDisciplinas do semestre " + semestre + ":");
            for (Disciplina disciplina : curriculo.getDisciplinas()) {
                System.out.println(" - " + disciplina.getCodigo() + " | " + disciplina.getNome());
            }
            System.out.print("Codigo da disciplina: ");
            String codigo = scanner.nextLine().trim();
            for (Disciplina disciplina : curriculo.getDisciplinas()) {
                if (disciplina.getCodigo().equals(codigo)) {
                    return codigo;
                }
            }
            System.out.println("Codigo de disciplina invalido para o semestre selecionado. Tente novamente.");
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
