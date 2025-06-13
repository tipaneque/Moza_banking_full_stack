# 💻 Internet Banking Full Stack Application

Este repositório contém uma aplicação full stack de Internet Banking com autenticação JWT, gestão de contas, transferências e extratos.

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

Este projecto é composto por duas partes: **Backend (Spring Boot)** e **Frontend (Angular)**. Siga as instruções abaixo para executar localmente.

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
spring.datasource.url=jdbc:mysql://localhost:3306/mozadb
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
cd ../frontend/mozaBanking
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

## 🧪 Login

O banco de dados ja tem usuários pré cadastrados
1. user: admim1, password: admin123
2. user: cliente1: password: senha123

Para criar contas siga os seguintes passos:
No banco de dados tem 10 users cliente1, cliente2, ..., cliente10
com senhas iguais - senha123
e dois admins, admin1 e admin2 com senhas iguais - admin123
1. Logar com conta de um dos admins;
2. no campo "Username do Cliente" instroduza um dos usernames dos clientes listados acima.
3. Complete os outros campos e clique no botão"criar conta" e uma conta será criada e associada ao
   user escolhido.

## 📫 Contato

Caso tenha dúvidas ou sugestões, entre em contato.
+258 868660661 | miropedrolino@gmail.com
---

