# 💻 Internet Banking Full Stack Application

Este repositório contém uma aplicação full stack de Internet Banking com autenticação JWT, gerenciamento de contas, transferências e extratos.

## 🧩 Tecnologias Utilizadas

### Backend (Java - Spring Boot)

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- MySQL

### Frontend (Angular)

- Angular 17+
- Angular Material UI

---

## 🚀 Deployment / Execução do Projeto

Este projeto é composto por duas partes: **Backend (Spring Boot)** e **Frontend (Angular)**. Siga as instruções abaixo para executar localmente.

### ✅ Requisitos

- Java 17 ou superior
- Maven (ou Gradle)
- Node.js v18+ e npm (ou yarn)
- Angular CLI:
  ```bash
  npm install -g @angular/cli
  ```

---

## 📦 Backend (Spring Boot)

### 1. Acesse a pasta do backend

```bash
cd backend
```

### 2. Configure o banco de dados (MySQL)

Edite `src/main/resources/application.properties` com as credenciais corretas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Compile e execute

Com Maven:

```bash
./mvnw spring-boot:run
```

Com Gradle:

```bash
./gradlew bootRun
```

A API estará disponível em: 📍 `http://localhost:8080/api/v1`

---

## 💻 Frontend (Angular)

### 1. Acesse a pasta do frontend

```bash
cd ../frontend
```

### 2. Instale as dependências

```bash
npm install
```

### 3. Execute em modo dev

```bash
ng serve
```

Aplicativo acessível em: 🌐 `http://localhost:4200`

---

## 🔐 Autenticação JWT

- O login retorna um token JWT.
- O token é armazenado localmente no frontend e adicionado automaticamente nas requisições autenticadas.

---

## 📦 Build para Produção

### Frontend:

```bash
ng build --prod
```

### Backend:

```bash
./mvnw clean package
# ou
./gradlew build
```

Rode o .jar gerado:

```bash
java -jar target/arquivo-gerado.jar
```

---

## 🧪 Testes

### Backend:

```bash
./mvnw test
```

### Frontend:

```bash
ng test
```

---

## 📫 Contato

Caso tenha dúvidas ou sugestões, entre em contato.

---

> Feito com 💙 para fins de aprendizado e demonstração.

