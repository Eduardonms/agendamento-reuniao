#Agendamento de Reunião — Full Stack
Sistema completo para cadastro de agendamentos de reunião:

##Backend: Java 21 + Quarkus 3 (REST API, JWT, H2/MySQL)
Frontend: Angular 19
Infra: Docker Compose com MySQL
Estrutura
agendamento-reuniao-api/
├── src/                 # Backend Quarkus
├── frontend/            # Frontend Angular
├── docker-compose.yml   # MySQL + API
└── Dockerfile           # Build da API
Pré-requisitos
Java 21
Node.js 18+
Docker (opcional, para MySQL/API em containers)
Backend (API)
Desenvolvimento com H2
.\mvnw.cmd quarkus:dev
API: http://localhost:8080
Swagger: http://localhost:8080/swagger-ui

##Autenticação JWT
Método	Rota	Descrição
POST	/api/auth/register	Cadastro de usuário
POST	/api/auth/login	Login (retorna token JWT)
Rotas de agendamento exigem header:

Authorization: Bearer <token>
Agendamentos
Método	Rota	Descrição
GET	/api/agendamentos	Listar (filtros: status, responsavel, inicio, fim)
GET	/api/agendamentos/{id}	Buscar por ID
POST	/api/agendamentos	Criar
PUT	/api/agendamentos/{id}	Atualizar
DELETE	/api/agendamentos/{id}	Excluir
Frontend (Angular)
cd frontend
npm install
npm start
App: http://localhost:4200

#Fluxo:

Cadastre-se em /register ou entre em /login
Após login, gerencie agendamentos em /agendamentos
Docker Compose (MySQL + API)
Subir apenas MySQL:

docker compose up mysql -d
Subir MySQL + API:

docker compose up --build
Variáveis usadas pela API no container:

DB_URL=jdbc:mysql://mysql:3306/agendamento_reuniao
DB_USERNAME=root
DB_PASSWORD=root
Executar tudo localmente
Terminal 1 — API:

.\mvnw.cmd quarkus:dev
Terminal 2 — Frontend:

cd frontend
npm start
Testes backend
.\mvnw.cmd test
Build produção
Backend:

.\mvnw.cmd package -Dquarkus.profile=prod

##Frontend:

cd frontend
npm run build
Exemplo — login
POST /api/auth/login
{
  "email": "usuario@email.com",
  "senha": "senha123"
}
##Resposta:

{
  "token": "eyJ...",
  "tipo": "Bearer",
  "nome": "Usuario",
  "email": "usuario@email.com"
}
##Exemplo — agendamento
POST /api/agendamentos
Authorization: Bearer <token>
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
