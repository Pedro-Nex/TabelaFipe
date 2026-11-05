# Consulta Tabela FIPE

Aplicação de console feita em Java que consulta a [API FIPE](https://deividfortuna.github.io/fipe/) para encontrar os valores de carros, motos e caminhões por marca, modelo e ano.

Escolha o tipo de veículo, informe a marca e busque o modelo pelo nome — por exemplo, `Palio`. Depois de selecionar um modelo, o programa consulta todos os anos disponíveis e exibe o valor, a marca, o modelo, o ano e o combustível.

## Funcionalidades

- Consulta carros, motos e caminhões.
- Lista as marcas disponíveis para o tipo escolhido.
- Filtra modelos pelo nome informado, sem diferenciar maiúsculas de minúsculas ou acentos.
- Mostra os valores FIPE de todos os anos disponíveis para o modelo selecionado.
- Permite fazer várias consultas na mesma execução.
- Informa quando há entradas inválidas, resultados vazios ou falha ao acessar a API.

## Tecnologias

- Java 21
- Spring Boot
- Maven
- API FIPE (`https://parallelum.com.br/fipe/api/v1`)

## Pré-requisitos

- JDK 21 instalado.
- Conexão com a internet para acessar a API.

O Maven Wrapper já está incluído no projeto; não é necessário instalar o Maven separadamente.

## Como executar

Clone o repositório e entre na pasta:

```powershell
git clone https://github.com/Pedro-Nex/TabelaFipe.git
cd TabelaFipe
```

Inicie a aplicação no Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No macOS ou Linux:

```bash
./mvnw spring-boot:run
```

## Como usar

1. Escolha `1 - Carro`, `2 - Moto` ou `3 - Caminhão`. Também é possível digitar o nome do tipo.
2. Informe o código da marca na lista apresentada.
3. Digite o nome ou parte do nome do modelo que deseja procurar.
4. Escolha o código do modelo correto entre os resultados.
5. Consulte os valores retornados para os anos disponíveis.
6. Digite `s` para iniciar outra consulta ou `n` para encerrar.

Exemplo do fluxo:

```text
Digite uma das opções para consultar valores: 1
Digite o código da marca: [código exibido na lista]
Digite o nome do modelo que deseja consultar (ex.: Palio): Palio
Digite o código do modelo para consultar valores: [código exibido nos resultados]
```

Os códigos e resultados são obtidos da API durante a execução; não há uma lista fixa de veículos no projeto.

## Executar os testes

```powershell
.\mvnw.cmd test
```

## Como a consulta funciona

A aplicação segue as etapas da API: marcas, modelos da marca, anos do modelo e consulta de valor para cada ano. O endereço da API pode ser configurado em `src/main/resources/application.properties`.

Os valores da Tabela FIPE são referências de preço médio e podem não corresponder ao preço efetivo de negociação de um veículo específico. Consulte as [informações da API](https://deividfortuna.github.io/fipe/) para detalhes.
