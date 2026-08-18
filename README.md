# Refactoring Lab - Route Closure Service

Este repositório é um projeto de estudo focado na **construção e arquitetura de um microserviço de encerramento de roteiros de logística**. O objetivo principal é demonstrar a implementação de um fluxo complexo de domínio utilizando **Arquitetura Hexagonal (Ports and Adapters)**, o padrão de projeto **Strategy**, a camada de apresentação com **MVC** e integração com mensageria e banco NoSQL.

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

O projeto adota a **Arquitetura Hexagonal (Ports & Adapters)** integrada com **MVC** na camada de entrada HTTP:

```text
src/main/java/com/refactoringlab/
│
├── domain/                               <--- DOMÍNIO (Núcleo Puro, sem anotações do Spring)
│   ├── model/                            <--- Entidades de Domínio (PedidoEn, Roteiro, etc.)
│   ├── strategy/                         <--- Padrão Strategy para tratativas do encerramento
│   │   ├── EncerramentoPedidoStrategy.java
│   │   └── impl/                         <--- Estratégias (Sinistro, Entregue, Reentrega, etc.)
│   ├── usecase/                          <--- Caso de Uso Principal (EncerrarRoteiroUseCase)
│   └── ports/                            <--- Contratos (Interfaces)
│       ├── input/                        <--- Porta do Caso de Uso
│       └── output/                       <--- Interfaces para Mongo, RabbitMQ e Rastreio
│
└── infrastructure/                       <--- INFRAESTRUTURA (Adapters & Frameworks)
    ├── adapters/
    │   ├── input/
    │   │   └── rest/                     <--- CAMADA MVC (REST Controller e DTOs)
    │   │       ├── EncerramentoRoteiroController.java
    │   │       └── dto/
    │   │
    │   └── output/                       <--- Adapters Concretos
    │       ├── mongodb/                  <--- Repositórios e Persistência
    │       └── rabbitmq/                 <--- Produtores de Mensagens
    └── config/                           <--- Configurações de Beans e Contexto Spring
