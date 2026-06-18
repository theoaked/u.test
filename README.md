# u.test — simulados & vestibulares (modernizado)

Plataforma de simulados/vestibulares (originalmente um TCC). Perfis de
**Administrador**, **Professor** e **Aluno**; cadastro de áreas, instituições,
questões (objetivas/dissertativas), provas (manuais e aleatórias), eventos,
correção de gabaritos e geração de relatório PDF.

Este repositório foi **modernizado** mantendo as características visuais e o
máximo da lógica original.

## Stack

| Camada | Tecnologia |
|---|---|
| Runtime | **Java 21**, **Spring Boot 3.3** (JAR executável, Tomcat embutido) |
| Web/MVC | Spring MVC + **Thymeleaf** (visual Bootstrap 3 preservado) |
| Persistência | Spring Data JPA / **Hibernate 6** (Jakarta) |
| Banco | **PostgreSQL** (H2 em memória nos testes) |
| Segurança | Spring Security + **BCrypt** |
| Relatórios | JasperReports |
| Build | **Maven** (com wrapper `mvnw`) |

## Pré-requisitos

- **JDK 21** (`JAVA_HOME` apontando para ele)
- **PostgreSQL** acessível (veja configuração abaixo)
- Maven é opcional — use o wrapper `./mvnw` (Linux/Mac) ou `mvnw.cmd` (Windows)

## Configuração do banco

Padrões (sobrescrevíveis por variáveis de ambiente) em
`src/main/resources/application.yml`:

| Variável | Padrão |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/postgres` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `12345` |
| `DDL_AUTO` | `update` (o Hibernate cria/atualiza o schema) |
| `PORT` | `8080` |

> O schema é gerado pelo Hibernate. Na primeira execução, o `DataSeeder`
> insere dados de demonstração **apenas se o banco estiver vazio**.

### Credenciais de demonstração (senha: `12345`)

| Login | Perfil |
|---|---|
| `admin` | Administrador |
| `prof` | Professor |
| `aluno` | Aluno |

## Build e execução

```bash
# Windows
mvnw.cmd clean package
java -jar target/utest-2.0.0.jar

# Linux/Mac
./mvnw clean package
java -jar target/utest-2.0.0.jar
```

Ou em modo desenvolvimento:

```bash
mvnw.cmd spring-boot:run
```

Acesse: http://localhost:8080/

## Testes

```bash
mvnw.cmd test
```

Os testes sobem o contexto completo num **H2 em memória** (não exigem PostgreSQL).

## Notas da modernização

- `javax.*` → `jakarta.*` (Jakarta EE 10).
- View migrada de **JSP** para **Thymeleaf**, preservando o HTML/CSS/Bootstrap 3.
  Cabeçalho e rodapé viraram *fragments* (`templates/template/*.html`).
- Os nomes de coluna **camelCase** originais (`tipoUsuario`, `dataInativa`,
  `aplicacoesA`...) foram preservados via
  `PhysicalNamingStrategyStandardImpl`, para não quebrar o SQL nativo nem o
  schema legado.
- Senhas agora em **BCrypt**; login (`UsuarioController`) e cadastros passam
  pelo `PasswordEncoder`.
- Os `*DaoJDBC` deixaram de manter uma `Connection` em cache (anti-padrão) e
  usam `JdbcTemplate`.
- `order by rand()` (MySQL) na seleção de questões aleatórias foi trocado por
  embaralhamento em memória (independente de dialeto).
- O `RelatorioServlet` (@WebServlet, `DriverManager`) virou um controller Spring
  usando o `DataSource` gerenciado e o `.jrxml` lido do classpath.

### Pontos de atenção (decisões deliberadas)

- **Controle de acesso**: mantido o gate por atributos de sessão nos controllers
  (lógica original). O Spring Security está configurado para BCrypt e com a
  cadeia de filtros permissiva; aplicar regras de URL por papel exigiria
  reestruturar todas as rotas. CSRF está desabilitado para não exigir token em
  todas as telas.
- **Relatório PDF**: o template `Cherry.jrxml` é o exemplo original (não um
  relatório de domínio real); a infraestrutura foi migrada, o conteúdo mantido.
