# 🗓️ Agendamento de Reunião — Full Stack

Sistema completo e moderno para gerenciamento e cadastro de agendamentos de reuniões.

---

## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 21 + Quarkus 3 (REST API, JWT, H2/MySQL)
* **Frontend:** Angular 19
* **Infraestrutura:** Docker Compose com MySQL

---

## 📂 Estrutura do Projeto

```text
agendamento-reuniao-api/
├── src/                    # Código-fonte do Backend Quarkus
├── frontend/               # Código-fonte do Frontend Angular
├── docker-compose.yml      # Configuração do MySQL + API
└── Dockerfile              # Script de Build da API
```

---

## 📋 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
* **Java 21**
* **Node.js 18+**
* **Docker** (opcional, para rodar o banco/API em containers)

---

## 🚀 Como Executar o Projeto Localmente

### ☕ Backend (API)

**Desenvolvimento com Banco H2 (Em Memória):**
```bash
# Na raiz do projeto backend
.\mvnw.cmd quarkus:dev
```
* **API local:** `http://localhost:8080`
* **Swagger UI (Documentação):** `http://localhost:8080/swagger-ui`

### 🅰️ Frontend (Angular)

```bash
# Acesse a pasta do frontend
cd frontend

# Instale as dependências
npm install

# Inicie o servidor de desenvolvimento
npm start
```
* **Aplicação web:** `http://localhost:4200`

### 🐳 Rodando Tudo Juntos (Terminal Único)

| Terminal | Comando |
| :--- | :--- |
| **Terminal 1 — API** | `.\mvnw.cmd quarkus:dev` |
| **Terminal 2 — Frontend** | `cd frontend && npm start` |

---

## 🐳 Ambiente Docker (MySQL + API)

**Subir apenas o banco MySQL:**
```bash
docker compose up mysql -d
```

**Subir a aplicação completa (MySQL + API):**
```bash
docker compose up --build
```

### 🔑 Variáveis de Ambiente da API no Container
* `DB_URL=jdbc:mysql://mysql:3306/agendamento_reuniao`
* `DB_USERNAME=root`
* `DB_PASSWORD=root`

---

## 🔐 Autenticação JWT

> **Nota:** As rotas de agendamento exigem o cabeçalho HTTP: `Authorization: Bearer <seu_token>`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Cadastro de novo usuário |
| `POST` | `/api/auth/login` | Login do usuário (retorna o token JWT) |

### 🛠️ Exemplo de Fluxo
1. Cadastre-se em `/register` ou faça login em `/login`.
2. Após obter o token, gerencie seus agendamentos no endpoint correspondente.

---

## 📅 Endpoints de Agendamentos

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/agendamentos` | Listar agendamentos (Filtros: `status`, `responsavel`, `inicio`, `fim`) |
| `GET` | `/api/agendamentos/{id}` | Buscar agendamento por ID |
| `POST` | `/api/agendamentos` | Criar um novo agendamento |
| `PUT` | `/api/agendamentos/{id}` | Atualizar um agendamento existente |
| `DELETE` | `/api/agendamentos/{id}` | Excluir um agendamento |

---

## 📝 Exemplos de Requisições e Respostas

### 🔑 Realizando Login (`POST /api/auth/login`)

**Request Body:**
```json
{
  "email": "usuario@email.com",
  "senha": "senha123"
}
```

**Response Body:**
```json
{
  "token": "eyJ...",
  "tipo": "Bearer",
  "nome": "Usuario",
  "email": "usuario@email.com"
}
```

### 🗓️ Criando um Agendamento (`POST /api/agendamentos`)

**Headers:**
```http
Authorization: Bearer <seu_token_jwt_aqui>
```

**Request Body:**
```json
{
  "titulo": "Reunião de planejamento",
  "descricao": "Alinhar sprint",
  "responsavel": "Maria Silva",
  "participantes": "João, Ana, Pedro",
  "localReuniao": "Sala 3",
  "dataHoraInicio": "2026-07-01T10:00:00",
  "dataHoraFim": "2026-07-01T11:00:00",
  "status": "AGENDADO"
}
```

---

## 🧪 Testes e Build de Produção

### Rodar Testes do Backend
```bash
.\mvnw.cmd test
```

### Compilar para Produção

**Backend:**
```bash
.\mvnw.cmd package -Dquarkus.profile=prod
```

**Frontend:**
```bash
cd frontend
npm run build
```
