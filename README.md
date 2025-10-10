# 📚 Biblioteca Online

Este é um projeto de biblioteca online desenvolvido com Spring Boot (backend) e React + TypeScript (frontend), permitindo o gerenciamento completo de uma biblioteca digital.

## 📸 Screenshots

<!-- Adicione aqui screenshots da sua aplicação. Por exemplo:
![Página Inicial](./screenshots/home.png)
![Lista de Livros](./screenshots/livros.png)
![Empréstimos](./screenshots/emprestimos.png)
-->

## 🚀 Requisitos

- Java 21
- Node.js
- PostgreSQL
- Maven

## 🗄️ Configuração do Banco de Dados

1. Instale o PostgreSQL em sua máquina se ainda não estiver instalado
2. Crie um banco de dados PostgreSQL
3. Configure as seguintes variáveis no arquivo `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=admin
   spring.jpa.properties.hibernate.default_schema=biblioteca
   ```
   > Notas: 
   > - Ajuste as configurações acima de acordo com sua instalação local do PostgreSQL
   > - O sistema utiliza o schema `biblioteca` no PostgreSQL. Este schema será criado automaticamente se não existir, devido à configuração `spring.jpa.hibernate.ddl-auto=update`

## ⚙️ Como Executar o Projeto

### 🔙 Backend (Spring Boot)

1. Navegue até a pasta do backend:
   ```bash
   cd backend
   ```

2. Execute o projeto usando Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   No Windows, use:
   ```bash
   mvnw.cmd spring-boot:run
   ```

O backend estará disponível em `http://localhost:8080`

### 🔝 Frontend (React + TypeScript)

1. Navegue até a pasta do frontend:
   ```bash
   cd frontend
   ```

2. Instale as dependências:
   ```bash
   npm install
   ```

3. Execute o projeto:
   ```bash
   npm run dev
   ```

O frontend estará disponível em `http://localhost:5173`

## 📂 Estrutura do Projeto

- `backend/`: Aplicação Spring Boot
  - `src/main/java/`: Código fonte Java
  - `src/main/resources/`: Arquivos de configuração
  - `pom.xml`: Dependências do Maven

- `frontend/`: Aplicação React
  - `src/`: Código fonte TypeScript/React
  - `public/`: Arquivos estáticos
  - `package.json`: Dependências NPM

## ✨ Funcionalidades

- 📚 Gerenciamento de livros
- 👥 Gerenciamento de usuários
- 📖 Sistema de empréstimos
- 🔍 Integração com Google Books API
- 💡 Recomendações de livros

## 🛠️ Tecnologias Utilizadas

### Backend
- ![Spring](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring&logoColor=white) Spring Boot
- ![JPA](https://img.shields.io/badge/JPA_&_Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white) JPA & Hibernate
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white) PostgreSQL
- ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white) Maven

### Frontend
- ![React](https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=react&logoColor=black) React
- ![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white) TypeScript
- ![Vite](https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white) Vite
- ![Tailwind](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=flat-square&logo=tailwind-css&logoColor=white) Tailwind CSS

## 📝 Detalhes Técnicos

### Backend
O backend foi desenvolvido utilizando Spring Boot com uma arquitetura em camadas:

- **JPA & Hibernate**: Utilizado para o mapeamento objeto-relacional (ORM), permitindo uma integração suave entre o código Java e o banco de dados PostgreSQL
- **Repository Pattern**: Implementado através do Spring Data JPA para abstração do acesso aos dados
- **DTO Pattern**: Utilizado para transferência segura de dados entre as camadas
- **Service Layer**: Contém toda a lógica de negócio da aplicação
- **Controller Layer**: Responsável pela exposição das APIs REST