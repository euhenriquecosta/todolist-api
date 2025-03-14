# TodoList API

API de gerenciamento de tarefas desenvolvida com Spring Boot e PostgreSQL.

## Requisitos

- Java 17
- Maven
- Docker e Docker Compose (para o PostgreSQL)

## Configuração do Banco de Dados

O projeto utiliza PostgreSQL como banco de dados. Para facilitar a configuração, um arquivo `docker-compose.yml` está disponível na raiz do projeto.

### Configuração das variáveis de ambiente

1. Crie um arquivo `.env` na raiz do projeto baseado no arquivo `.env.example`:
   ```bash
   cp .env.example .env
   ```

2. Edite o arquivo `.env` com suas credenciais:
   ```
   POSTGRES_USER=seu_usuario
   POSTGRES_PASSWORD=sua_senha_segura
   POSTGRES_DB=todolist
   ```

### Iniciando o PostgreSQL

Para iniciar o PostgreSQL:

```bash
docker-compose up -d postgres
```

Isso irá iniciar um container PostgreSQL com as configurações definidas no seu arquivo `.env`.

## Executando a Aplicação

Para executar a aplicação:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

## Endpoints

### Usuários

- **POST /users/**: Criar um novo usuário
- **GET /users/{id}**: Obter um usuário pelo ID

### Tarefas

- **POST /tasks/create**: Criar uma nova tarefa
- **GET /tasks/list**: Listar todas as tarefas do usuário
- **PUT /tasks/update/{id}**: Atualizar uma tarefa existente

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- BCrypt para criptografia de senhas 