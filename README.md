# ByteBank API

API REST bancária desenvolvida em Java e Spring Boot para demonstrar a aplicação prática de padrões de projeto GoF e abstrações do Spring Framework.

## Objetivo

Permitir criação e consulta de contas, depósitos, saques e consulta do histórico de transações, aplicando separação de responsabilidades, validações e persistência.

## Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA / Hibernate
- Bean Validation
- H2 Database
- Maven
- JUnit 5

## Padrões de projeto

### Strategy (GoF — comportamental)
`OperacaoStrategy` define um contrato para operações. `DepositoStrategy` e `SaqueStrategy` implementam comportamentos diferentes, permitindo adicionar novas operações sem concentrar todas as regras em um único método.

### Factory (GoF — criacional)
`OperacaoFactory` seleciona a estratégia pelo tipo da operação. As implementações são injetadas pelo Spring por meio de um `Map<String, OperacaoStrategy>`.

### Observer (GoF — comportamental)
`ContaService` publica `OperacaoRealizadaEvent` com `ApplicationEventPublisher`. `OperacaoEventListener` recebe o evento com `@EventListener`, desacoplando a execução bancária da reação ao evento.

### Singleton (GoF — criacional)
Os componentes anotados com `@Component` e `@Service` usam o escopo singleton do Spring por padrão: o contêiner mantém uma instância compartilhada de cada bean por ApplicationContext. O projeto demonstra o uso do Singleton gerenciado pelo contêiner, sem implementar um singleton manual.

## Abstrações Spring utilizadas

- **IoC e DI:** o contêiner cria e injeta repositories, services, strategies e listeners.
- **Spring Web:** controllers REST e mapeamento HTTP.
- **Spring Data JPA:** repositories e persistência.
- **Bean Validation:** validação dos DTOs com `@Valid`, `@NotBlank`, `@NotNull` e `@DecimalMin`.
- **Tratamento global de erros:** `@RestControllerAdvice` e `@ExceptionHandler`.
- **Transações:** `@Transactional`.
- **Eventos:** `ApplicationEventPublisher` e `@EventListener`.

## Como executar

Requisitos: JDK 21 e Maven 3.9+.

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

Executar os testes:

```bash
mvn test
```

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/contas` | Criar conta |
| GET | `/api/contas/{id}` | Consultar conta e saldo |
| POST | `/api/contas/{id}/operacoes` | Depositar ou sacar |
| GET | `/api/contas/{id}/transacoes` | Consultar histórico |

## Exemplos de uso

### 1. Criar conta

```http
POST /api/contas
Content-Type: application/json
```

```json
{
  "titular": "Patrick",
  "saldoInicial": 500.00
}
```

Resposta (exemplo):

```json
{
  "id": 1,
  "titular": "Patrick",
  "saldo": 500.00
}
```

### 2. Depositar

```http
POST /api/contas/1/operacoes
Content-Type: application/json
```

```json
{
  "tipo": "DEPOSITAR",
  "valor": 150.00
}
```

### 3. Sacar

```json
{
  "tipo": "SACAR",
  "valor": 50.00
}
```

Envie o JSON acima para `POST /api/contas/1/operacoes`.

### 4. Consultar conta

```http
GET /api/contas/1
```

### 5. Consultar histórico

```http
GET /api/contas/1/transacoes
```

## Banco H2

Console disponível em `http://localhost:8080/h2-console`.

- JDBC URL: `jdbc:h2:mem:bytebankdb`
- User: `sa`
- Password: (vazio)

O banco é em memória e reinicia quando a aplicação é encerrada.

## Regras de negócio

- O valor de depósito ou saque deve ser maior que zero.
- Não é permitido sacar mais do que o saldo disponível.
- Operações aceitas: `DEPOSITAR` e `SACAR`.
- Operações inválidas não alteram o saldo.
- As transações são registradas no histórico.

## Melhorias futuras

- Autenticação e autorização com Spring Security.
- Banco PostgreSQL.
- Documentação OpenAPI/Swagger.
- Paginação do histórico.
- Idempotência e auditoria avançada.

## Autor

Projeto desenvolvido para demonstrar conhecimentos em Java, Spring Boot, orientação a objetos, padrões GoF e desenvolvimento de APIs REST.
