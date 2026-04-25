## Teste com Docker
Criei um container docker Postgres que já está com as mesmas credenciais do projeto, para rodar:
```cmd
docker-compose up -d
```

## Teste em Postgres local
Para alterar as credenciais do banco de dados, alterar os dados do arquivo `application-postgres.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/{nome_do_banco}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username={username}
spring.datasource.password={senha}
```
