# Refactoring Lab - Route Closure Service

Este repositório é um projeto de estudo focado na **construção e arquitetura de um microserviço de encerramento de roteiros de logística**. O objetivo principal é demonstrar a implementação de um fluxo de domínio com **Clean Architecture**, mantendo regras de negócio isoladas e a infraestrutura como detalhe de implementação.

---

## 🎯 Objetivos do Projeto

1. **Design Extensível com Strategy Pattern:** Implementar o encerramento de pedidos de forma desacoplada, permitindo que novas regras de tratativa (Sinistro, Entregue, Reentrega, Insucesso) sejam adicionadas sem alterar o fluxo principal.
2. **Isolamento do Domínio (Clean Architecture):** Manter o núcleo das regras de negócio livre de dependências do framework (Spring), do banco de dados (**MongoDB**) e da mensageria (**RabbitMQ**).
3. **Alta Testabilidade:** Garantir a criação de testes unitários isolados para cada estratégia de encerramento sem a necessidade de subir o contexto do Spring ou dependências de infraestrutura.
4. **Aplicação Prática dos Princípios SOLID:** Foco em **Single Responsibility Principle (SRP)** e **Open/Closed Principle (OCP)** para garantir fácil manutenção e evolução.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.x**
    - Spring Web (MVC)
    - Spring Data MongoDB
    - Spring AMQP (RabbitMQ)
- **MongoDB** (Persistência de Pedidos e Roteiros)
- **RabbitMQ** (Publicação de eventos de atualização de roteiro e lançamentos de sinistro)
- **JUnit 5 & Mockito** (Testes unitários e de integração)
- **Docker & Docker Compose** (Ambiente de desenvolvimento local)

---

## 📐 Arquitetura do Projeto

O projeto adota **Clean Architecture** com separação de responsabilidades entre domínio, aplicação e infraestrutura:

```text
src/main/java/br/com/refactoringlab/
│
├── domain/                               <--- Regras de negócio puras
│   ├── entities/                         <--- Entidades (ex.: Pedido)
│   ├── valueobjects/                     <--- Objetos de valor (ex.: Endereco)
│   ├── enums/                            <--- Enumerações de negócio
│
├── application/                          <--- Casos de uso e orquestração
│   ├── usecases/                         <--- Regras de aplicação
│   ├── dto/                              <--- DTOs de entrada da aplicação
│   └── gateways/                         <--- Contratos para serviços externos
│
└── infrastructure/                       <--- Frameworks e detalhes externos
    ├── controllers/                      <--- Camada HTTP (Spring MVC + DTOs)
    ├── db/mongodb/repository/            <--- Repositórios Spring Data
    ├── db/mongodb/gateway/               <--- Implementações concretas dos gateways
    └── config/                           <--- Wiring de beans Spring
