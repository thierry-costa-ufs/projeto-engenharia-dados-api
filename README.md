# Sistema de Gestão Universitária — Persistência Poliglota & Escrita Dupla

## 📋 Sobre o Projeto
Este projeto consiste no desenvolvimento de um ecossistema corporativo distribuído projetado para operar em cenários de **persistência poliglota** e **consistência eventual**. O sistema gerencia registros acadêmicos baseados no esquema universitário da UFS, manipulando as entidades de `Usuário`, `Professor`, `Estudante`, `Disciplina`, `Turma` e `Vínculo`.

O ecossistema é dividido em duas frentes integradas:
1. **Back-End (API RESTful):** Desenvolvido em **Java e Spring Boot**, operando transações simultâneas em estruturas relacionais (**PostgreSQL na AWS RDS**) e orientadas a documentos (**MongoDB na AWS EC2**).
2. **Front-End (Painel de Resiliência):** Desenvolvido em **React + Vite**, atuando como um barramento transacional no cliente (Saga Pattern) com tolerância a falhas parciais.

---

## 🏗️ Arquitetura e Engenharia de Resiliência

### 1. Escrita Dupla (Dual Write) & Persistência Saga no Cliente
Para garantir a integridade em sistemas distribuídos sem o overhead de um Two-Phase Commit (2PC) tradicional, o front-end orquestra as inserções através do hook customizado `useSagaPersistence`.
* Quando ocorre uma falha de sincronismo ou escrita parcial entre os bancos primário e secundário, o sistema intercepta o fluxo e renderiza o `ResilienceModal`.
* Permite ao operador aplicar estratégias em tempo de execução: **Rollback** da transação incompleta ou **manutenção forçada no banco estável (Keep / Postgres Only)**.

### 2. Camada Defensiva (Zod Validation)
A validação de payloads foi movida para uma camada estrita e isolada no cliente (`src/utils/validators/`). Utilizando o operador `safeParse` do **Zod**, o front-end mapeia erros de entrada (formatos de CPF, constraints de tamanho de string, limites de créditos e anos de ingresso) cirurgicamente abaixo de cada input, blindando a API de requisições malformadas.

### 3. Fontes de Verdade Unificadas (Enums Centralizados)
Eliminação completa de *strings mágicas* no código. Domínios discretos como titulações acadêmicas, regimes de jornadas de trabalho (`20h`, `40h`, `DE`) e status de vínculos acadêmicos são gerenciados exclusivamente em `src/utils/constants/enums.js`, alimentando dinamicamente tanto as views quanto os schemas do Zod.

---

## 🛠️ Tecnologias e Infraestrutura

### Back-End (API)
* **Linguagem / Framework:** Java (LTS) & Spring Boot
* **Camada Relacional:** Spring Data JPA / Hibernate & PostgreSQL (AWS RDS)
* **Camada NoSQL:** Spring Data MongoDB & MongoDB (AWS EC2)
* **Gerenciador de Dependências:** Maven
* **Arquitetura:** RESTful com tráfego de DTOs

### Front-End (Dashboard)
* **Ecossistema:** React 19 & Vite
* **Validação:** Zod (Esquemas em tempo de execução)
* **Ícones / Estilos:** Lucide React & CSS Modules (`*.module.css`)

---

## 📂 Estrutura de Pastas Chave (Front-End)

```text
src/
├── components/
│   ├── shared/
│   │   └── ResilienceModal.jsx     # Modal de tratamento de falhas da Saga
│   └── forms/
│       ├── UsuarioForm.jsx         # Form com inserção dinâmica de múltiplos e-mails/tels
│       ├── ProfessorForm.jsx       # Form integrado com Enums estáticos de jornada
│       └── ...
├── hooks/
│   └── useSagaPersistence.js       # Orquestrador assíncrono da Escrita Dupla
├── utils/
│   ├── constants/
│   │   └── enums.js                # Centralização de domínios fixos (Single Source of Truth)
│   └── validators/
│       ├── index.js                # Barrel de exportação dos validadores
│       ├── professorValidator.js
│       └── ...
└── styles/
    └── FormTheme.module.css        # Identidade visual unificada do ecossistema de formulários

🚀 Como Executar o Ecossistema
Configuração do Back-End (Spring Boot)

    Certifique-se de configurar as strings de conexão ativas das instâncias AWS RDS e EC2 no arquivo src/main/resources/application.properties.

    Execute a aplicação via Maven:
    Bash

    ./mvnw spring-boot:run

Configuração do Front-End (React + Vite)

    Acesse o diretório do front-end e instale as dependências:
    Bash

    npm install

    Inicie o servidor de desenvolvimento com suporte a HMR:
    Bash

    npm run dev

👥 Desenvolvedores (Grupo)

    Thierry N. C. S. Costa — Ciência da Computação (UFS)

    Cauã S. Corumba — Ciência da Computação (UFS)

    Guilherme G. Araújo — Ciência da Computação (UFS)

🧑‍🏫 Orientação e Contexto Acadêmico

    Professor Orientador: Dr. André Britto de Carvalho

    Disciplina: Engenharia de Dados

    Instituição: Universidade Federal de Sergipe (UFS) — Campus São Cristóvão