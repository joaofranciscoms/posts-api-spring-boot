## Posts API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![SpringSecurity](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

API REST de postagens e comentários construída com Spring Boot, Spring Data JPA, Spring Security, JWT e PostgreSQL. A autenticação é *stateless*: o usuário faz login, recebe um token JWT e o envia no cabeçalho `Authorization` das demais requisições. O acesso aos recursos é controlado por perfis (`ADMIN`, `AUTOR` e `LEITOR`).

## Tecnologias e versões

- **Java**: 21
- **Spring Boot**: 3.3.4
- **Spring Framework**: 6.1.13 (versão gerenciada automaticamente pelo `spring-boot-starter-parent` 3.3.4)
- **Spring Security** com autenticação via **JWT** (biblioteca `java-jwt` da Auth0, algoritmo HMAC256) e senhas criptografadas com **BCrypt**
- **PostgreSQL** como banco de dados
- **MapStruct** e **Lombok**
- **springdoc-openapi** (anotações OpenAPI nos controllers)

## Boas práticas adotadas

- **Tratamento centralizado de exceções**: uma classe `@RestControllerAdvice` (`GlobalExceptionHandler`) captura exceções como corpo de requisição malformado, falha de autenticação, acesso negado e recurso não encontrado, devolvendo respostas de erro em formato padronizado:

  ```json
  {
      "timestamp": "2025-01-01T12:00:00",
      "status": 401,
      "error": "Login ou senha inválidos"
  }
  ```

- **DTOs (Data Transfer Objects)**: as entidades não são expostas diretamente pelos endpoints. Objetos de requisição e resposta são representados por DTOs, isolando o modelo de domínio da camada de API.
- **Mapeamento com MapStruct**: a conversão entre entidades e DTOs é feita por mappers do MapStruct, eliminando conversões manuais repetitivas.
- **Validação (Bean Validation)**: o login, a criação de posts e a criação de comentários validam o corpo da requisição com `@Valid`. Em caso de dados inválidos, a API responde com `400 Bad Request`.
- **Busca paginada**: os endpoints de listagem (`GET /api/posts` e `GET /api/comentarios`) são paginados, evitando retornar grandes volumes de dados de uma só vez.
- **Autorização por perfil**: as regras de acesso são declaradas nos controllers com `@PreAuthorize`.

---

## Como executar o projeto

1. Clone o repositório:
   ```
   https://github.com/joaofranciscoms/posts-api-spring-boot.git
   ```
2. Instale o PostgreSQL e crie um banco de dados chamado `posts-db`. As tabelas são criadas e atualizadas automaticamente pelo Hibernate (`ddl-auto: update`).
3. Instale o Postman e crie uma Collection com o nome de sua preferência.
4. Abra o projeto na sua IDE (recomendo o IntelliJ) e aguarde a instalação automática das dependências via Maven.
5. Se necessário, ajuste as configurações no `application.yml`. Os valores padrão já funcionam em um PostgreSQL local:
   ```yaml
   spring:
     datasource:
       url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/posts-db}
       username: ${DATABASE_USERNAME:postgres}
       password: ${DATABASE_PASSWORD:postgres}
   ```
   ```yaml
   app:
     jwt:
       secret: cG9zdHNhcGlqd3RzZWNyZXQ=
   ```

   > **Por que as credenciais estão expostas?** As credenciais do banco de dados e o segredo do JWT aparecem no `application.yml` de propósito: a ideia é que, após clonar o repositório, a API já suba pronta para uso, bastando ter o PostgreSQL instalado e o banco `posts-db` criado.
   >
   > As configurações do banco podem ser sobrescritas pelas variáveis de ambiente `DATABASE_URL`, `DATABASE_USERNAME` e `DATABASE_PASSWORD`, como indicado na sintaxe `${VARIAVEL:valor-padrao}`. O segredo do JWT pode ser sobrescrito pela variável de ambiente `APP_JWT_SECRET`.
   >
   > **Importante**: esses valores padrão servem apenas para ambiente local de desenvolvimento/testes. Em produção, é essencial sobrescrevê-los com credenciais seguras, nunca utilizando os valores expostos aqui.

6. Suba a aplicação. A API estará disponível em `http://localhost:8080`.

---

## Autenticação

A API usa JWT. Apenas `POST /auth/login` e `POST /auth/register` são públicos; **todos os outros endpoints exigem um token válido**, inclusive as consultas (`GET`).

### 1. Registrar um usuário

No Postman, crie uma requisição `POST` para `http://localhost:8080/auth/register`, selecione o formato JSON (`Body > raw > JSON`) e envie:

```json
{
    "login": "usuarioTeste",
    "password": "usuarioTeste123",
    "role": "AUTOR"
}
```

Perfis disponíveis: `ADMIN`, `AUTOR` e `LEITOR`. A resposta é `200 OK` sem corpo; se o login já existir, a resposta é `400 Bad Request`.

### 2. Fazer login e obter o token

Crie uma requisição `POST` para `http://localhost:8080/auth/login` com o corpo:

```json
{
    "login": "usuarioTeste",
    "password": "usuarioTeste123"
}
```

A resposta traz o token JWT, que expira em **2 horas**. Credenciais inválidas retornam `401 Unauthorized`.

### 3. Usar o token nas requisições

Em cada requisição aos demais endpoints, envie o cabeçalho:

```
Authorization: Bearer <seu-token>
```

No Postman, isso pode ser feito pela aba **Authorization**, escolhendo o tipo **Bearer Token** e colando o token. Para facilitar, configure a autorização na própria Collection, para que todas as requisições herdem o token.

---

## Endpoints e permissões

### Auth

| Método | URL              | Descrição                         | Acesso  |
|:------:|:-----------------|:----------------------------------|:--------|
| `POST` | `/auth/register` | Registra um usuário               | Público |
| `POST` | `/auth/login`    | Autentica e devolve um token JWT  | Público |

### Post

| Método   | URL               | Descrição                         | Perfis permitidos              |
|:--------:|:------------------|:----------------------------------|:-------------------------------|
| `POST`   | `/api/posts`      | Cria um post                      | `AUTOR`, `ADMIN`               |
| `GET`    | `/api/posts/{id}` | Busca um post                     | Qualquer usuário autenticado   |
| `PUT`    | `/api/posts/{id}` | Atualiza um post                  | `AUTOR`, `ADMIN`               |
| `DELETE` | `/api/posts/{id}` | Deleta um post                    | `AUTOR`, `ADMIN`               |
| `GET`    | `/api/posts`      | Filtra/lista posts (paginado)     | Qualquer usuário autenticado   |

### Comentário

| Método   | URL                                | Descrição                              | Perfis permitidos              |
|:--------:|:-----------------------------------|:---------------------------------------|:-------------------------------|
| `POST`   | `/api/posts/{idPost}/comentarios`  | Cria um comentário em um post          | `AUTOR`, `ADMIN`, `LEITOR`     |
| `GET`    | `/api/comentarios/{id}`            | Busca um comentário                    | Qualquer usuário autenticado   |
| `PUT`    | `/api/comentarios/{id}`            | Atualiza um comentário                 | `AUTOR`                        |
| `DELETE` | `/api/comentarios/{id}`            | Deleta um comentário                   | `AUTOR`, `ADMIN`, `LEITOR`     |
| `GET`    | `/api/comentarios`                 | Filtra/lista comentários (paginado)    | Qualquer usuário autenticado   |

Os identificadores (`{id}` e `{idPost}`) são UUIDs.

### Filtros e paginação

Os endpoints de listagem aceitam os seguintes parâmetros de consulta (*query params*), todos opcionais:

| Parâmetro        | Endpoints                           | Descrição                               | Padrão |
|:-----------------|:------------------------------------|:----------------------------------------|:------:|
| `titulo`         | `GET /api/posts`                    | Filtra posts pelo título                | —      |
| `conteudo`       | `GET /api/posts`, `GET /api/comentarios` | Filtra pelo conteúdo               | —      |
| `pagina`         | `GET /api/posts`, `GET /api/comentarios` | Número da página (começa em 0)     | `0`    |
| `tamanho-pagina` | `GET /api/posts`, `GET /api/comentarios` | Quantidade de itens por página     | `10`   |

Exemplo:

```
GET http://localhost:8080/api/posts?titulo=lorem&pagina=0&tamanho-pagina=5
```

### Códigos de resposta comuns

| Código | Significado                                                            |
|:------:|:-----------------------------------------------------------------------|
| `200`  | Consulta realizada com sucesso                                         |
| `201`  | Recurso criado (a URL do novo recurso vem no cabeçalho `Location`)     |
| `204`  | Atualização ou exclusão realizada com sucesso                          |
| `400`  | Erro de validação ou corpo da requisição ausente/malformado            |
| `401`  | Token ausente/inválido ou login e senha incorretos                     |
| `403`  | O perfil do usuário não tem permissão para o recurso                   |
| `404`  | Recurso não encontrado                                                 |

---

## Exemplos de JSON

**Registrar usuário** — `POST /auth/register`
```json
{
    "login": "joao",
    "password": "joao123",
    "role": "AUTOR"
}
```

**Login** — `POST /auth/login`
```json
{
    "login": "joao",
    "password": "joao123"
}
```

**Criar Post** — `POST /api/posts`
```json
{
    "titulo": "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
    "conteudo": "Eum voluptatem beatae itaque, ut non autem iusto inventore ratione, praesentium provident facilis. Inventore cumque maxime nulla adipisci voluptatem odit veniam nesciunt."
}
```

**Atualizar Post** — `PUT /api/posts/{id}`
```json
{
    "titulo": "consectetur adipisicing elit. Minus, reprehenderit voluptates aut odit incidunt.",
    "conteudo": "numquam omnis! Et facilis molestias cupiditate culpa vel sapiente."
}
```

**Criar Comentário** — `POST /api/posts/{idPost}/comentarios`
```json
{
    "conteudo": "At error, aperiam qui incidunt non accusantium laborum quae inventore architecto officiis ratione sapiente perspiciatis eum!"
}
```

**Atualizar Comentário** — `PUT /api/comentarios/{id}`
```json
{
    "conteudo": "Lorem ipsum dolor sit amet consectetur adipisicing elit. Eligendi, nostrum magnam illo a saepe pariatur."
}
```
