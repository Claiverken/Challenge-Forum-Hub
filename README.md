# 📖 Descrição
O Fórum Hub é uma API REST desenvolvida como parte do Challenge de Back-End da Alura. A aplicação simula o back-end de um fórum de discussão, permitindo que os utilizadores façam perguntas sobre determinados tópicos, criem respostas, e interajam com o conteúdo. O foco do projeto foi construir uma API segura, robusta e que segue as melhores práticas do mercado, utilizando o ecossistema Spring.
# 🛠️ Tecnologias Utilizadas
- **Java 24**

- **Spring Boot 3**

- **Spring Security (com JWT)**

- **Spring Data JPA / Hibernate**

- **PostgreSQL**

- **Flyway**

- **Maven**

- **Lombok**
# ✨ Funcionalidades Principais
- **Cadastro de usuários:** Endpoint público para que novos utilizadores se possam registar na plataforma.
- **Autenticação e Autorização:** Sistema de segurança completo com Tokens JWT para proteger os endpoints.
- **Gestão de Tópicos (CRUD):** Operações completas para criar, listar, detalhar, atualizar e apagar tópicos, com regras de negócio para permissões.
- **Gestão de Respostas:** Funcionalidade para que utilizadores autenticados possam responder a tópicos existentes.
- **Paginação:** A listagem de tópicos é paginada para otimizar a performance e a escalabilidade da API.
- **Tratamento de Erros:** Sistema centralizado para tratamento de exceções, que retorna respostas de erro JSON padronizadas e informativas.
- **Gestão de Base de Dados com Migrations:** Utilização do Flyway para controlar a evolução do esquema da base de dados de forma versionada e segura.
# 🚀 Como Executar o Projeto
## Pré-requisitos
- JDK 24 (ou superior) instalado.

- Maven instalado.

- Uma instância do PostgreSQL a ser executada.
## Configuração
- Clone o repositório: ```https://github.com/Claiverken/Challenge-Forum-Hub.git```

- Cria uma base de dados no PostgreSQL chamada ```Challenge_Forum_Hub```.

- Configura as tuas variáveis de ambiente ou altera o ficheiro ```src/main/resources/application.properties``` com as tuas credenciais:

```
Properties

# Endereço do teu servidor PostgreSQL
DB_HOST=localhost:5432

# Nome da base de dados
DB_NAME=Challenge_Forum_Hub

# O teu utilizador do PostgreSQL
DB_USER=postgres

# A tua senha do PostgreSQL
DB_PASSWORD=tua_senha_secreta

# Um segredo para a assinatura dos tokens JWT
JWT_SECRET=minha_chave_secreta_super_longa_e_segura
```
Nota: No ficheiro ```application.properties```, as variáveis estão como ```${DB_HOST}```, etc.. É recomendado que cries estas variáveis de ambiente no teu sistema.
## Execução
1. Clona o repositório.

2. Abre o projeto na tua IDE de preferência.

3. Executa a aplicação através da classe principal ChallengeForumHubApplication.java.

O Flyway irá criar e popular a base de dados automaticamente no primeiro arranque.
# 🔎 Endpoints da API
A seguir estão os principais endpoints disponíveis na API.
| Método | Endpoint | Autenticação? | Descrição |
| :--- | :--- | :--- | :--- |
| `POST` | `/usuarios` | **Não** | Regista um novo utilizador na plataforma. |
| `POST` | `/login` | **Não** | Autentica um utilizador e retorna um token JWT. |
| `GET` | `/topicos` | **Não** | Lista todos os tópicos ativos (paginado e ordenado por data). |
| `GET` | `/topicos/{id}` | **Não** | Exibe os detalhes de um tópico específico, incluindo as suas respostas. |
| `POST` | `/topicos` | **Sim** | Cria um novo tópico. O autor é o utilizador autenticado. |
| `PUT` | `/topicos/{id}` | **Sim** | Atualiza um tópico. Apenas o autor ou um admin pode executar. |
| `DELETE` | `/topicos/{id}` | **Sim** | Apaga (logicamente) um tópico. Apenas o autor ou um admin pode executar. |
| `POST` | `/topicos/{id}/respostas` | **Sim** | Adiciona uma nova resposta a um tópico. |
