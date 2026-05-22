# API de Persistência Poliglota - Engenharia de Dados (UFS)

## 📋 Sobre o Projeto
Este projeto consiste no desenvolvimento de uma API RESTful corporativa utilizando o ecossistema **Java, Spring Boot e Hibernate**, projetada para operar em cenários de persistência poliglota. A aplicação realiza operações completas de manipulação de dados (CRUD) simultaneamente em estruturas relacionais (**PostgreSQL**) e orientadas a documentos (**MongoDB**), ambos hospedados e gerenciados em instâncias na nuvem (**AWS**).

O escopo do sistema abrange o gerenciamento de registros acadêmicos baseados no esquema do ecossistema universitário, manipulando as entidades de `Usuário`, `Aluno (Estudante)`, `Curso` e `Matrícula (Vínculo)`.

---

## 🛠️ Tecnologias e Infraestrutura
* **Linguagem:** Java (LTS)
* **Framework Base:** Spring Boot
* **Persistência Relacional:** Spring Data JPA / Hibernate & PostgreSQL (AWS RDS)
* **Persistência NoSQL:** Spring Data MongoDB & MongoDB (AWS EC2)
* **Gerenciador de Dependências:** Maven
* **Padronização de API:** RESTful arquitetura com tráfego de DTOs

---

## 📂 Estrutura de Camadas (Arquitetura)
O projeto adota o padrão de arquitetura em camadas industriais para garantir baixo acoplamento e manutenibilidade:
* `controller/`: Camada de exposição dos endpoints HTTP e validação de payloads.
* `service/`: Camada detentora das regras de negócio e emulação de restrições de integridade para o ecossistema NoSQL.
* `repository/`: Interfaces de comunicação ativa com os drivers Spring Data.
* `model/`: Mapeamento estrutural das entidades (subdividido em `relational` e `nosql`).
* `dto/`: Objetos de transferência de dados para proteção das entidades de domínio durante o tráfego na rede.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
* Java JDK instalado e configurado nas variáveis de ambiente.
* Maven instalado (ou utilização do wrapper `./mvnw` incluso).

### Configuração das Credenciais
Antes de iniciar a aplicação, certifique-se de configurar as variáveis de ambiente ou o arquivo `src/main/resources/application.properties` com as strings de conexão ativas das instâncias AWS RDS e EC2.

### Execução via Linha de Comando
```bash
# Clonar o repositório
git clone [https://github.com/SEU_USUARIO/projeto-engenharia-dados-api.git](https://github.com/SEU_USUARIO/projeto-engenharia-dados-api.git)

# Acessar a pasta do projeto
cd projeto-engenharia-dados-api

👥 Desenvolvedores (Grupo)

    Thierry N. C. S. Costa - Ciência da Computação (UFS)

    Cauã S. Corumba - Ciência da Computação (UFS)

    Guilherme G. Araújo - Ciência da Computação (UFS)

🧑‍🏫 Orientação

    Professor: Dr. André Britto de Carvalho

    Disciplina: Engenharia de Dados

    Instituição: Universidade Federal de Sergipe (UFS)

# Compilar e executar a aplicação
./mvnw spring-boot:run
