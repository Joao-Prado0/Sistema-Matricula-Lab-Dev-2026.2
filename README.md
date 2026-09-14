# Sistema de Matrículas Universitário

Projeto desenvolvido para a disciplina de **Projeto de Software** do curso de Engenharia de Software da **PUC Minas**, sob orientação da Profa. Milena Menezes Adão.

O objetivo é projetar e implementar um sistema que informatize o processo de matrículas de uma universidade, permitindo que a secretaria gerencie o currículo semestral, os alunos se matriculem e cancelem matrículas em disciplinas, os professores consultem suas turmas e o sistema de cobranças seja notificado automaticamente sobre as inscrições confirmadas.

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Como executar o projeto](#como-executar-o-projeto)
- [Integrantes do grupo](#integrantes-do-grupo)
- [Modelo de análise](#modelo-de-análise)
  - [Diagrama de casos de uso](#diagrama-de-casos-de-uso)
  - [Histórias de usuário](#histórias-de-usuário)
  - [Matriz de rastreabilidade](#matriz-de-rastreabilidade)
---

## Sobre o projeto

O sistema atende às seguintes necessidades, conforme especificado pelo Product Owner:

- A secretaria gera o currículo de cada semestre letivo e mantém os cadastros de cursos, disciplinas, professores e alunos.
- Cada curso possui nome, número de créditos e é composto por diversas disciplinas.
- Os alunos podem se matricular em até 4 disciplinas obrigatórias (1ª opção) e mais 2 disciplinas optativas (alternativas), dentro de um período regulamentar de matrículas.
- Uma disciplina só permanece ativa para o semestre seguinte se tiver, no mínimo, 3 alunos matriculados ao fim do período; caso contrário, é cancelada. O limite máximo é de 60 alunos por disciplina.
- Após a confirmação da matrícula, o sistema de cobranças externo é notificado para gerar a cobrança correspondente. O mesmo ocorre em caso de cancelamento, para o devido ajuste de fatura.
- Professores podem consultar os alunos matriculados em cada uma de suas disciplinas.
- Todos os usuários acessam o sistema por meio de login e senha.

Este README documenta a entrega do **Lab01S01** (Modelo de Análise): Diagrama de Casos de Uso e Histórias de Usuário.

## Tecnologias utilizadas

- **Java** (Java puro, sem frameworks adicionais nesta etapa do projeto)

## Como executar o projeto

Nesta fase (Lab01S01) o projeto ainda não possui código-fonte implementado — a entrega corresponde exclusivamente aos modelos de análise (diagrama de casos de uso e histórias de usuário) apresentados abaixo. As instruções de execução serão adicionadas a partir do Lab01S02, quando as classes, atributos e stubs de métodos forem criados, e detalhadas com o comando de compilação/execução Java assim que o protótipo estiver disponível (Lab01S03):

```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd <nome-do-repositorio>

# Compilar (a partir do Lab01S02/S03, quando houver código-fonte)
javac -d bin src/**/*.java

# Executar
java -cp bin <PacotePrincipal>.Main
```

## Integrantes do grupo

- João Prado Campos
- Filipe Gabriel Costa Araujo
- Daniel Bony Costa Garcia
- Rafael Henriques Aquino Correa

## Modelo de análise

### Diagrama de casos de uso

![Diagrama de Casos de Uso do Sistema de Matrículas](docs/DiagramaDeCasoUso.png)

O diagrama contempla os quatro atores do sistema — **Aluno**, **Professor**, **Secretaria** e **Sistema de Cobranças** (ator externo) — e os seguintes casos de uso:

| Ator | Casos de uso |
| :--- | :--- |
| Aluno | Matricular em disciplina (1ª opção e optativas), Cancelar matrícula, Consultar grade de matrícula |
| Professor | Consultar alunos matriculados por disciplina |
| Secretaria | Gerenciar currículo semestral (cursos e disciplinas), Manter cadastro de alunos e professores, Processar fechamento do período / validar disciplinas |
| Sistema de Cobranças | Recebe a notificação do caso de uso "Notificar sistema de cobranças" |

Observações sobre a modelagem:

- Todos os casos de uso relacionados a Aluno, Professor e Secretaria possuem um relacionamento `<<include>>` com **Efetuar login**, já que a autenticação é pré-requisito para qualquer operação no sistema.
- O caso de uso **Gerenciar currículo semestral (cursos e disciplinas)** consolida, propositalmente, a geração do currículo do semestre e o cadastro/manutenção de cursos e disciplinas (US02 e US03 nas histórias de usuário abaixo), por serem operações realizadas em conjunto pela Secretaria.
- O caso de uso **Notificar sistema de cobranças** é incluído tanto por **Matricular em disciplina** quanto por **Cancelar matrícula**, pois o sistema financeiro deve ser notificado tanto na confirmação quanto no cancelamento de uma matrícula (para reajuste de fatura).

### Histórias de usuário

Este documento apresenta a especificação de requisitos do **Sistema de Matrículas** no formato de **Histórias de Usuário (User Stories)**, conforme solicitado para a entrega do **Lab01S01**.

#### Personas identificadas

- **Secretaria / Administrador do Sistema:** responsável pela gestão curricular de cada semestre e manutenção dos registros cadastrais de cursos, disciplinas, professores e alunos.
- **Aluno:** usuário que realiza e cancela matrículas em disciplinas obrigatórias e optativas durante os períodos letivos estipulados.
- **Professor:** usuário acadêmico que consulta suas turmas/disciplinas e a relação nominal de alunos nelas matriculados.
- **Sistema de Cobrança (Ator Externo / Sistema Integrado):** subsistema financeiro que recebe notificações automáticas das matrículas consolidadas dos alunos para geração de cobranças.

#### Épico 1: Autenticação e Gestão de Acesso

**US01 – Autenticação no Sistema (Login)**
- **Como** usuário do sistema (Aluno, Professor ou Secretaria),
- **Quero** realizar autenticação informando meu identificador/matrícula e senha,
- **Para que** eu possa acessar os recursos e funcionalidades pertinentes ao meu perfil com segurança.

*Critérios de aceitação:*
- O sistema deve validar as credenciais fornecidas contra a base de usuários cadastrados.
- Em caso de credenciais inválidas, uma mensagem de erro clara deve ser exibida e o acesso bloqueado.
- Cada usuário só deve ter acesso às telas e operações autorizadas para seu papel específico.

#### Épico 2: Gestão Acadêmica e Curricular (Secretaria)

**US02 – Gestão do Currículo Semestral**
- **Como** membro da Secretaria da universidade,
- **Quero** gerar e estruturar o currículo do semestre letivo,
- **Para que** as disciplinas e cursos disponíveis para matrícula sejam disponibilizados aos estudantes.

*Critérios de aceitação:*
- O currículo deve ser vinculado a um semestre letivo específico (ex.: 2026/2).
- Deve permitir associar as disciplinas ofertadas ao semestre.

**US03 – Cadastro e Manutenção de Cursos e Disciplinas**
- **Como** membro da Secretaria da universidade,
- **Quero** cadastrar, atualizar e listar cursos e suas respectivas disciplinas,
- **Para que** o catálogo curricular esteja completo e com as regras acadêmicas configuradas.

*Critérios de aceitação:*
- Cada curso deve conter nome, número de créditos e a relação de disciplinas que o compõem.
- Cada disciplina deve possuir nome, código, ementa/créditos e capacidade de vagas (mínimo de 3 e máximo de 60).
- A secretaria pode definir se uma disciplina é ofertada como obrigatória ou optativa na grade curricular.

> No diagrama de casos de uso, US02 e US03 estão representadas por um único caso de uso — **Gerenciar currículo semestral (cursos e disciplinas)** — por serem executadas em conjunto pela Secretaria.

**US04 – Manutenção Cadastral de Alunos e Professores**
- **Como** membro da Secretaria da universidade,
- **Quero** manter (cadastrar, alterar, desativar e consultar) os dados de alunos e professores,
- **Para que** as informações acadêmicas e de acesso permaneçam atualizadas.

*Critérios de aceitação:*
- O cadastro de cada usuário deve incluir nome, identificador único (matrícula), e-mail e credencial de acesso (senha inicial).
- O cadastro de alunos deve permitir o vínculo a um determinado curso de graduação.

> Corresponde ao caso de uso **Manter cadastro de alunos e professores** no diagrama.

**US05 – Fechamento do Período de Matrículas e Validação de Disciplinas**
- **Como** membro da Secretaria da universidade (ou processo automático do sistema),
- **Quero** processar o encerramento do período de matrículas ao fim do prazo regulamentar,
- **Para que** apenas as disciplinas viáveis fiquem ativas para o semestre subsequente.

*Critérios de aceitação:*
- A disciplina só fica ativa (vai ocorrer no semestre seguinte) se tiver **no mínimo 3 alunos inscritos**.
- Caso uma disciplina possua menos de 3 alunos inscritos no término do período, o sistema deve marcá-la como **cancelada**.
- Os alunos matriculados em disciplinas canceladas devem ser notificados/ter o status atualizado no sistema.

> Corresponde ao caso de uso **Processar fechamento do período / validar disciplinas** no diagrama.

#### Épico 3: Processo de Matrícula (Aluno)

**US06 – Realização de Matrícula em Disciplinas**
- **Como** aluno da universidade,
- **Quero** selecionar e me matricular nas disciplinas ofertadas no semestre dentro do período regulamentar,
- **Para que** eu possa cursar os componentes curriculares necessários à minha formação.

*Critérios de aceitação:*
- A matrícula só pode ser realizada se o período de matrícula estiver aberto e ativo.
- O aluno pode se matricular em no máximo **4 disciplinas como 1ª opção (obrigatórias)**.
- O aluno pode se matricular em no máximo **2 disciplinas alternativas (optativas)**.
- O sistema não deve permitir que o aluno exceda esses limites de disciplinas.
- Cada disciplina suporta no máximo **60 alunos**. Ao atingir esse limite, as inscrições para ela devem ser bloqueadas/encerradas imediatamente para novos alunos.

**US07 – Cancelamento de Matrícula**
- **Como** aluno da universidade,
- **Quero** cancelar a minha matrícula em uma ou mais disciplinas previamente selecionadas durante o período de matrículas,
- **Para que** eu possa ajustar minha grade horária conforme minha disponibilidade.

*Critérios de aceitação:*
- O cancelamento só é permitido enquanto o período de matrículas estiver vigente.
- Ao cancelar a matrícula em uma disciplina, a vaga ocupada deve ser liberada imediatamente para outros estudantes.
- A listagem de matrículas ativas do aluno deve refletir a exclusão em tempo real.
- O sistema de cobranças deve ser renotificado para o devido ajuste da fatura do aluno (ver US10).

**US08 – Consulta de Comprovante e Grade de Matrícula**
- **Como** aluno da universidade,
- **Quero** visualizar a lista de disciplinas em que estou matriculado no semestre atual,
- **Para que** eu possa confirmar minha grade horária e status de inscrição.

*Critérios de aceitação:*
- Exibir a identificação das disciplinas (código, nome, tipo: obrigatória ou optativa).
- Exibir a situação atual da disciplina (ativa, em formação, cancelada).

> Corresponde ao caso de uso **Consultar grade de matrícula** no diagrama.

#### Épico 4: Gestão de Turmas (Professor)

**US09 – Consulta de Alunos Matriculados por Disciplina**
- **Como** professor da universidade,
- **Quero** acessar o sistema e consultar a relação de alunos matriculados em cada uma das minhas disciplinas,
- **Para que** eu possa planejar as aulas e acompanhar a composição das minhas turmas.

*Critérios de aceitação:*
- O professor autenticado deve visualizar apenas as disciplinas atribuídas a ele no semestre.
- Para cada disciplina, o sistema deve listar os nomes e matrículas de todos os alunos efetivamente inscritos.
- A lista deve indicar a quantidade total de alunos matriculados na turma.

#### Épico 5: Integração Financeira (Sistema de Cobranças)

**US10 – Notificação Automática do Sistema de Cobranças**
- **Como** Sistema de Matrículas (serviço interno),
- **Quero** notificar o Sistema de Cobranças externo assim que um aluno concluir/atualizar suas inscrições semestrais,
- **Para que** a cobrança das mensalidades/créditos das disciplinas seja calculada e faturada corretamente.

*Critérios de aceitação:*
- O envio do evento/notificação deve ocorrer após a confirmação da inscrição semestral do aluno (caso de uso **Matricular em disciplina**).
- A notificação deve conter a identificação do aluno, a lista de disciplinas confirmadas e a somatória de créditos correspondente.
- Em caso de cancelamento durante o período (caso de uso **Cancelar matrícula**), o sistema financeiro deve ser renotificado para reajuste das faturas — ou seja, o mesmo caso de uso **Notificar sistema de cobranças** é acionado a partir de ambos os fluxos.

### Matriz de rastreabilidade

| Requisito do Enunciado do Laboratório | História(s) de Usuário | Caso de Uso no Diagrama |
| :--- | :--- | :--- |
| Secretaria gera currículo semestral | US02 | Gerenciar currículo semestral (cursos e disciplinas) |
| Secretaria mantém disciplinas, professores e alunos | US03, US04 | Gerenciar currículo semestral / Manter cadastro de alunos e professores |
| Cursos possuem nome, créditos e diversas disciplinas | US03 | Gerenciar currículo semestral (cursos e disciplinas) |
| Aluno pode matricular em 4 obrigatórias (1ª opção) e 2 optativas (alternativas) | US06 | Matricular em disciplina |
| Período delimitado para efetuar e cancelar matrículas | US06, US07 | Matricular em disciplina / Cancelar matrícula |
| Disciplina ativa se tiver pelo menos 3 alunos; caso contrário, é cancelada | US05 | Processar fechamento do período / validar disciplinas |
| Limite máximo de 60 alunos por disciplina (inscrições encerradas ao atingir) | US06 | Matricular em disciplina |
| Notificação ao sistema de cobranças após inscrição do aluno no semestre | US10 | Notificar sistema de cobranças |
| Professores consultam alunos matriculados em cada disciplina | US09 | Consultar alunos matriculados por disciplina |
| Validação de acesso via senha para todos os usuários | US01 | Efetuar login |
| Visualização de grade/status da inscrição pelo aluno | US08 | Consultar grade de matrícula |