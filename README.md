# SGHSS - Sistema de Gestão de Saúde e Serviços de Saúde

Um sistema para gerenciar informações de pacientes, médicos, consultas e prontuários em uma instituição de saúde.

##  Descrição

O SGHSS é uma aplicação backend desenvolvida com Spring Boot que oferece uma API RESTful completa para:

-  **Gestão de Pacientes**: Cadastro e manutenção de dados dos pacientes
-  **Gestão de Médicos**: Gerenciamento de profissionais de saúde
-  **Agendamento de Consultas**: Controle de consultas e agendamentos
-  **Prontuários Eletrônicos**: Manutenção de registros médicos

##  Tecnologias Utilizadas

- **Java 25**
- **Spring Boot 4.0.6**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Lombok**
- **Maven**
- **Flyway** (para migrações de banco de dados)

##  Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- Java 25 ou superior
- Maven 3.6+
- PostgreSQL 12+ 

##  Instalação

### 1. Clonar o repositório

```bash
git clone https://github.com/DaviRM91/sghss.git
cd sghss
```

### 2. Configurar o banco de dados

Crie um banco de dados PostgreSQL chamado `sghss` e configure as credenciais no arquivo `src/main/resources/application.properties`:


```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sghss
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Compilar e executar

```bash
# Compilar o projeto
./mvnw clean install

# Executar a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

##  Estrutura do Projeto

```
src/main/java/com/vidaplus/sghss/
├── SghssApplication.java              # Classe principal
├── controller/                        # Controladores REST
│   ├── ConsultaController.java
│   ├── MedicoController.java
│   └── PacienteController.java
├── service/                          # Lógica de negócio
│   ├── ConsultaService.java
│   ├── MedicoService.java
│   └── PacienteService.java
├── repository/                       # Acesso a dados
│   ├── ConsultaRepository.java
│   ├── MedicoRepository.java
│   ├── PacienteRepository.java
│   └── ProntuarioRepository.java
├── model/                            # Entidades JPA
│   ├── Consulta.java
│   ├── Medico.java
│   ├── Paciente.java
│   └── Prontuario.java
├── dto/                              # Data Transfer Objects
│   ├── ConsultaDTO.java
│   ├── PacienteDTO.java
│   └── PacienteRequestDTO.java
├── exception/                        # Tratamento de exceções
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── security/                         # Configurações de segurança
    └── SecurityConfig.java
```

##  Endpoints Principais

### Pacientes

```
GET    /api/pacientes                 # Listar todos os pacientes
POST   /api/pacientes                 # Criar novo paciente
GET    /api/pacientes/{id}            # Obter paciente por ID
PUT    /api/pacientes/{id}            # Atualizar paciente
DELETE /api/pacientes/{id}            # Deletar paciente
```

### Médicos

```
GET    /api/medicos                   # Listar todos os médicos
POST   /api/medicos                   # Criar novo médico
GET    /api/medicos/{id}              # Obter médico por ID
PUT    /api/medicos/{id}              # Atualizar médico
DELETE /api/medicos/{id}              # Deletar médico
```

### Consultas

```
GET    /api/consultas                 # Listar todas as consultas
POST   /api/consultas                 # Agendar nova consulta
GET    /api/consultas/{id}            # Obter consulta por ID
PUT    /api/consultas/{id}            # Atualizar consulta
DELETE /api/consultas/{id}            # Cancelar consulta
```

##  Segurança

O projeto utiliza **Spring Security** para:

- Autenticação e autorização de usuários
- Proteção de endpoints sensíveis
- Validação de requisições

Configure as credenciais de segurança no arquivo `application.properties` ou variáveis de ambiente.

##  Testes

Para executar os testes:

```bash
./mvnw test
```

Os testes cobrem:
- Testes unitários de serviços
- Testes de integração com banco de dados
- Testes de validação de segurança

##  Migrações de Banco de Dados

O projeto utiliza **Flyway** para versionamento do schema do banco de dados.

As migrações estão localizadas em: `src/main/resources/db/migration/`

Para adicionar uma nova migração:

1. Crie um novo arquivo em `db/migration/` com o padrão: `V{numero}__{descricao}.sql`
2. Exemplo: `V002__create_consultas_table.sql`
3. A migração será executada automaticamente ao iniciar a aplicação



##  Exemplo de Uso

A API utiliza HTTP Basic Authentication com três perfis (roles) pré-definidos:
- **ADMIN**: Acesso total a todos os endpoints
- **SENHA: admin123**
- **MEDICO**: Acesso a endpoints relacionados a consultas e prontuários
- **SENHA: medico123**
- **PACIENTE**: Acesso a endpoints relacionados a pacientes e agendamento
- **SENHA: paciente123**
Foi utilizado o Postman para testar os endpoints da API.

### Criar um Paciente

```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
  "nome": "Ana Carolina Souza",
  "cpf": "12345678901",
  "dataNascimento": "1985-06-15",
  "email": "ana.souza@email.com",
  "telefone": "(11) 91234-5678",
  "endereco": "Rua das Flores, 123, Apto 45, São Paulo - SP"
}'
```

### Listar Pacientes

```bash
curl -X GET http://localhost:8080/api/pacientes
```
### Agendar uma Consulta

```bash
curl -X POST http://localhost:8080/api/consultas/agendar?pacienteId=1&medicoId=1&dataHora=2025-06-20T14:30:00
```
### Listar Consultas

```bash
curl -X GET /api/consultas/paciente/{pacienteId}
```
### Cancelar uma Consulta

```bash
curl -X DELETE /api/consultas/cancelar/{consultaId}
```
### Criar um medico

```bashcurl -X POST http://localhost:8080/api/medicos \
  -H "Content-Type: application/json" \
  -d '{
  "nome": "Dr. João Mendes",
  "crm": "CRM-12345/SP",
  "especialidade": "Cardiologia",
  "email": "joao.mendes@cardiologia.com",
  "telefone": "(11) 98765-4321"
}'
```
### Listar medicos

```bash 
curl -X GET http://localhost:8080/api/medicos
```
### Atualizar um medico

```bash
curl -X PUT http://localhost:8080/api/medicos/{id} \
  -H "Content-Type: application/json" \
  -d '{
  "nome": "Dr. João",
  "crm": "CRM-12345",
  "especialidade": "Cardiologia",
  "email": "joao@medico.com",
  "telefone": "(11) 99888-7777"
}'
```
### Deletar um medico

```bash
curl -X DELETE http://localhost:8080/api/medicos/{id}
```
### Licença

Esseprojeto foi desenvolvido para fins educacionais, como trabalho da disciplina Projeto Multidisciplinar do curso de Tecnologia em Análise e Desenvolvimento de Sistemas – UNINTER.

---


