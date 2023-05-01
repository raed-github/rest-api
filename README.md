# Create Account For Existing Customer

# Table of contents
- [Introduction](#Introduction)
- [Summary](#Summary)
- [Design](#Design)
- [ObjectModel](#ObjectModel)
- [account-service](#account-service)
- [customer-service](#customer-service)
- [transaction-service](#transaction-service)
- [TechStack](#TechStack)
- [Maven](#Maven)
- [Testing](#Testing)
___
### Spring Boot and ReactJS Application

---
### Introduction
This project provides to create account for existing customers.

### Summary
Create an API's to be used for opening a new “current account” of already existing
customers.

#### Requirements

- The API will expose an endpoint which accepts the user information (customerID,
initialCredit).

- Once the endpoint is called, a new account will be opened connected to the user whose ID is
customerID.

- Also, if initialCredit is not 0, a transaction will be sent to the new account.

- Another Endpoint will output the user information showing Name, Surname, balance, and
transactions of the accounts.

### Design

The application has 3 apis
* account-service
* customer-service
* transaction-service
* service-discovery

```html
POST /v1/customers/{customerId} - Create a customer
POST /v1/accounts - creates a new account for existing customer
GET /v1/customers/customer-info/{customerId} - retrieves customer information
GET /v1/customers - retrieves all customers
```
#### ObjectModel
- Account
- Transaction
- Customer

##### account-service

| API Name | HTTP Method | Model URIs | Status code | Description | 
| --------------- | --------------- | --------------- | --------------- | --------------- |
| account-service | POST| /v1/accounts | 200 (CREATED)| Creating an account |
| account-service | PUT | /v1/accounts/{id} | 200 (OK) |Updating account |
| account-service | GET | /v1/accounts/{id} | 200 (OK) | Fetching one account |
| account-service | DELETE | /v1/accounts/{id} | 204 (No Content) | Deleting an account |
| account-service | GET | /v1/accounts/customer-accounts/{id}| 200 (OK) | Fetch a customer accounts |

##### customer-service

| API Name | HTTP Method | Model URIs | Status code | Description | 
| --------------- | --------------- | --------------- | --------------- | --------------- |
| customer-service | POST| /v1/customers | 200 (CREATED)| Creating a customer |
| customer-service | PUT | /v1/customers/{id} | 200 (OK) |Updating customer |
| customer-service | GET | /v1/customers/{id} | 200 (OK) | Fetching a single customer |
| customer-service | DELETE | /v1/customers/{id} | 204 (No Content) | Deleting a customer |
| customer-service | GET | /v1/customers/customer-ifo/{id}| 200 (OK) | Fetch a customer info |

##### transaction-service

| API Name | HTTP Method | Model URIs | Status code | Description | 
| --------------- | --------------- | --------------- | --------------- | --------------- |
| transaction-service | POST| /v1/transactions | 200 (CREATED)| Creating an account |
| transaction-service | PUT | /v1/transactions/{id} | 200 (OK) |Updating account |
| transaction-service | GET | /v1/transactions/{id} | 200 (OK) | Fetching one account |
| transaction-service | DELETE | /v1/transactions/{id} | 204 (No Content) | Deleting an account |
| transaction-service | GET | /v1/transactions/customer-transactions/{customerId}| 200 (OK) | Fetch a customer transactions |


JUnit & integration tests coverage.

### TechStack

---
- Java 11
- Spring Boot
- Spring Data JPA
- Restful API
- Lombok
- H2 In memory database  
- Docker
- Docker compose
- JUnit 5
- ReactJS for frontend

### Prerequisites

---
- Maven
- Docker
- ReactJS
- Java 8
- Lombok

### Run & Build

---
There are 2 ways of run & build the application.

#### Docker Compose

For docker compose usage, docker images already push to docker.io

You just need to run `docker-compose up` command
___
*$PORT: *
```ssh
$ cd account
$ docker-compose up
```

#### Maven

For maven usage, you need to change `proxy` value in the `bank-frontend/package.json` 
file by `"http://localhost:8080"` due to the default value has been settled for docker image network proxy.
___
*$PORT: *
```ssh
$ cd rest-api/service-discovery
$ mvn clean install
$ mvn spring-boot:run

$ cd rest-api/account-service
$ mvn clean install
$ mvn spring-boot:run

$ cd rest-api/customer-service
$ mvn clean install
$ mvn spring-boot:run

$ cd rest-api/transaction-api
$ mvn clean install
$ mvn spring-boot:run

$ cd rest-api/front-end
$ npm install
$ npm start
```

### Swagger UI will be run on this url
`http://localhost:${PORT}/swagger-ui.html`

### Testing
