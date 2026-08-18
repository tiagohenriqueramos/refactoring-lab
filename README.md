# Refactoring Lab - Route Closure Service

Este repositório é um projeto de estudo e refatoração prática. O objetivo principal é pegar um fluxo legado de encerramento de roteiros de logística, originalmente estruturado em um modelo monolítico com forte acoplamento e estruturas condicionais extensas (`switch/case`), e reestruturá-lo aplicando **Arquitetura Hexagonal (Ports and Adapters)**, **Strategy Design Pattern** e a **camada de apresentação com MVC**.

---

## 🎯 Objetivos da Refatoração

1. **Eliminar Condicionais Rígidas:** Substituir o acoplamento do `switch (novoStatusTratativa)` por uma arquitetura extensível via **Strategy Pattern**.
2. **Desacoplar o Domínio da Infraestrutura:** Isolar as regras de negócio de encerramento de roteiros do framework (Spring), do banco de dados (**MongoDB**) e da mensageria (**RabbitMQ**).
3. **Facilitar Testabilidade:** Permitir o teste unitário de cada estratégia de encerramento (Entregue, Sinistro, Reentrega, Insucesso) de forma 100% isolada e sem dependências externas.
4. **Respeitar Princípios SOLID:** Aplicação direta do **Single Responsibility Principle (SRP)** e do **Open/Closed Principle (OCP)** — novos status de encerramento podem ser adicionados sem alterar o código do serviço principal.

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
│   ├── model/                            <--- Entidades de Domínio (PedidoEntrega, Roteiro, etc.)
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
