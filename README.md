# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

## Recursos

### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis.

**Endpoint:**
```http
GET /stocks
```
#### Exemplos de respostas
````JSON
Response
Status: 200
Content-Type: application/json
Body: {
    "count": 1,
    "message": "Método executado com sucesso!",
    "stockList": [
        {
        "id": "24d283f2-7133-4c43-b50d-882b0d7b4b3f",
        "symbol": "Ab3",
        "companyName": "mtstestess",
        "price": 13.0
        }
    ]
  }
````
````JSON
Response
Status: 404
Content-Type: application/json
Body: {
      "count":0,
      "message":"Não existem stocks cadastrados",
      "stockList":null
    }
````
### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```
#### Exemplos de respostas
````JSON
Response
Status: 200
Content-Type: application/json
Body: {
      "message": "Método executado com sucesso!",
      "stock": {
        "id": "24d283f2-7133-4c43-b50d-882b0d7b4b3f",
        "symbol": "Ab3",
        "companyName": "mtstestess",
        "price": 13.0
      }
  }
````
````JSON
Response
Status: 404
Content-Type: application/json
Body: {
      "message": "Não foi possível encontrar stock cadastrado com o id:24d283f2-7133-4c43-b50d-882b0d7b4b3",
      "stock": null
  }
````
### Criar uma Nova Ação
Cria uma nova ação com base nos dados fornecidos.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**
Todos os campos são obrigatórios*

| Campo       | Tipo    | Formato                                       |
|-------------|---------|-----------------------------------------------|
| symbol      | String  | Duas letras seguidas de um número. EX.: "Bb2" |
| companyName | String  | Nome da companhia que pertence a ação.        |
| price       | Decimal | Valor da ação.                                |        

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}
```
#### Exemplos de respostas
````JSON
Response
Status: 201
Content-Type: application/json
Body: {
    "id": "24d283f2-7133-4c43-b50d-882b0d7b4b3f",
    "symbol": "Ab3",
    "companyName": "mtstestess",
    "price": 13.0
  }
````
````JSON
Response
Status: 400
Content-Type: application/json
Body: {
      "symbol": "o Simbolo deve ter 3 letras seguidas de 1 número."
  }
````
````JSON
Response
Status: 400
Content-Type: application/json
Body: {
      "companyName": "O nome da empresa não pode ficar em branco."
  }
````
````JSON
Response
Status: 400
Content-Type: application/json
Body: {
      "price": "o preço não pode ser zero ou menor que zero."
  }
````
### Atualizar uma Ação por ID
Atualiza os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
PUT /stocks/{id}
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97
}
```
#### Exemplos de respostas
````JSON
Response
Status: 202
Content-Type: application/json
Body: {
        "message": "Método executado com sucesso!",
        "stock": {
            "id": "544236b5-ea42-4322-81b9-7a4466e7865d",
            "symbol": "BBAS3",
            "companyName": "Banco do Brasil SA",
            "price": 59.97
        }
    }
````
````JSON
Response
Status: 400
Content-Type: application/json
Body: {
      "symbol": "o Simbolo deve ter 2 letras seguidas de 1 número.(Ex.:Bs3)."
  }
````
````JSON
Response
Status: 400
Content-Type: application/json
Body: {
      "companyName": "O nome da empresa não pode ficar em branco."
  }
````
````JSON
Response
Status: 400
Content-Type: application/json
Body: {
      "price": "o preço não pode ser zero ou menor que zero."
  }
````
````JSON
Response
Status: 404
Content-Type: application/json
Body: {
        "message": "Não foi possível encontrar stock cadastrado com o id:544236b5-ea42-4322-81b9-7a4466e7865",
        "stock": null
    }
````
### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Endpoint:**
```http
PUT /stocks/{id}
```
#### Exemplos de respostas
````JSON
Response
Status: 200
Content-Type: application/json
Body: {
      "message": "Stock deletado com sucesso!"
     }
````

````JSON
Response
Status: 404
Content-Type: application/json
Body: {
      "message": "Não foi possível encontrar stock cadastrado com o id:4f50edf2-4d0e-45b8-82e9-40865cf3379c"
    }
````


## Uso
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos
- Java 11 ou superior
- Maven
- PGAdmin

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Contribuições
Contribuições são bem-vindas!


