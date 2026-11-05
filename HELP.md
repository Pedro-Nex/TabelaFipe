# Consulta Tabela FIPE

Aplicação de console em Java 21 e Spring Boot que consulta a API pública da FIPE.

## Executar

No Windows, abra o terminal na pasta do projeto e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Também é possível gerar e executar o JAR:

```powershell
.\mvnw.cmd package
java -jar target\TabelaFipe-0.0.1-SNAPSHOT.jar
```

## Como consultar

1. Escolha carro, moto ou caminhão (aceita `1`, `2`, `3` ou o nome).
2. Digite o código da marca exibida.
3. Digite parte do nome do modelo, por exemplo `Palio`.
4. Escolha o código do modelo correspondente.
5. A aplicação consulta cada ano disponível e exibe valor, marca, modelo, ano e combustível.

Ao final, digite `s` para iniciar outra consulta ou `n` para encerrar. É necessária uma conexão com a internet para acessar a API.

O endereço base da API pode ser alterado em `src/main/resources/application.properties`.
