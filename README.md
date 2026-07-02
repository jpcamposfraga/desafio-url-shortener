# Encurtador de URL (URL Shortener) - Desafio Backend-br

Este é um serviço de encurtamento de URLs desenvolvido em **Java** com **Spring Boot** e **PostgreSQL**. O projeto foi implementado com base no desafio técnico do [Backend-br](https://github.com/backend-br/desafios/blob/master/url-shortener/PROBLEM.md) com o objetivo de praticar conceitos avançados de backend, persistência, concorrência e arquitetura de APIs REST.

---

## 🚀 Funcionalidades

- **Encurtamento de URL:** Recebe uma URL original e gera um código de encurtamento alfanumérico seguro com comprimento dinâmico (entre 5 e 10 caracteres).
- **Redirecionamento HTTP 302:** Redireciona o usuário automaticamente para a URL original correspondente ao código curto.
- **Lógica de Expiração:** Os links gerados possuem prazo de validade (configurado para 5 minutos por padrão). Caso o link expire, o acesso retorna erro HTTP 404 (Not Found).
- **Validação de Protocolos:** Formatação automática das URLs originais (adicionando prefixo `https://` caso o usuário envie o link sem protocolo), evitando redirecionamentos relativos incorretos.
- **Tratamento Ativo de Colisões:** Algoritmo recursivo que verifica se o código alfanumérico gerado aleatoriamente já existe no banco de dados antes de salvá-lo, prevenindo que um link sobrescreva outro.
- **Agendador de Limpeza Automática (Cleanup Job):** Tarefa agendada via `@Scheduled` que roda a cada minuto deletando do banco de dados registros que já expiraram, otimizando o armazenamento físico.
- **Tratamento de Erros Global:** Centralizador de exceções (`@RestControllerAdvice`) que padroniza o retorno de erros para o cliente, convertendo exceções como `UrlNotFoundException` e `ExpiredUrlException` em respostas HTTP limpas e formatadas.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.x
- **Persistência & ORM:** Spring Data JPA / Hibernate
- **Banco de Dados:** PostgreSQL
- **Geração Criptográfica:** `SecureRandom` para geração de códigos altamente imprevisíveis
- **Produtividade:** Lombok (para redução de código boilerplate)
- **Gerenciador de Dependências:** Maven

---

## ⚙️ Configuração e Instalação

### Pré-requisitos
Antes de começar, certifique-se de ter instalado:
* **Java 21 JDK** ou superior
* **Maven**
* **PostgreSQL** rodando localmente

### 1. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/desafio-url-shortener.git
cd desafio-url-shortener
```

### 2. Configurar o Banco de Dados
A aplicação está configurada para conectar a um banco de dados PostgreSQL local chamado `url-shortener`. 
Crie a base de dados no seu PostgreSQL e ajuste as configurações no arquivo `src/main/resources/application.properties` caso as credenciais da sua máquina sejam diferentes:

```properties
spring.datasource.url = jdbc:postgresql://localhost:5432/url-shortener
spring.datasource.username = seu_usuario
spring.datasource.password = sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
app.base-url = http://localhost:8080
```

### 3. Rodar a aplicação
Na raiz do projeto, execute o comando:
```bash
mvn spring-boot:run
```
A aplicação iniciará na porta `8080` por padrão.

---

## 📌 Documentação da API

### 1. Criar Link Encurtado
Gera e retorna um link encurtado para uma determinada URL.

* **URL:** `/api/shorten-url`
* **Método:** `POST`
* **Headers:** `Content-Type: application/json`
* **Corpo (Request Body):**
```json
{
  "url": "https://google.com"
}
```

* **Resposta de Sucesso (200 OK):**
```json
{
  "url": "http://localhost:8080/api/aK8e2"
}
```

---

### 2. Redirecionamento
Redireciona para a URL original a partir do código encurtado.

* **URL:** `/api/{shortUrlCode}`
* **Método:** `GET`
* **Resposta de Sucesso (302 Found):**
  * Redireciona o navegador automaticamente para a URL correspondente (ex: `https://google.com`).
  * Header `Location` preenchido com a URL destino.

* **Resposta de Erro (404 Not Found) - Link Inexistente ou Expirado:**
```json
{
  "statusCode": 404,
  "message": "Short url not found",
  "timeStamp": "2026-07-02T16:05:00"
}
```
