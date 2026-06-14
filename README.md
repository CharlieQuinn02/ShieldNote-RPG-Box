# 🛡️ ShieldNote RPG-Box

> API REST de gerenciamento de campanhas e sessões de RPG de Mesa

---

## 👥 Equipe de Desenvolvimento

| Membro | Responsabilidades |
|---|---|
| **João Victor** | Módulos `Character`, `NPC`, `Monster`, `PLayer_Character` |
| **Khauan** | Módulos `User`,`Spells`, `SpellsCasting`, `SpellsSlots` |
| **Vinícius** | Módulos `Campaign`, `Notes`, `EncounterCalculator` |

---

## 📋 Sumário

1. [Descrição do Problema](#-descrição-do-problema)
2. [Requisitos e Casos de Uso](#-requisitos-e-casos-de-uso)
3. [Diagrama de Classes](#-diagrama-de-classes)
4. [Diagrama Entidade-Relacionamento (DER)](#️-diagrama-entidade-relacionamento-der)
5. [Projeto e Arquitetura](#️-projeto-e-arquitetura)
6. [Implementação](#-implementação)
7. [Conclusão](#-conclusão)
8. [Informações Adicionais](#ℹ️-informações-adicionais)

---

## 📌 Descrição do Problema

O universo dos jogos de RPG de Mesa (Role-Playing Games) exige que os mestres de campanha lidem simultaneamente com uma enorme quantidade de informações: fichas de personagens, estatísticas de monstros, anotações de sessão, controle de encontros, histórias, cenários e personagens não jogáveis (NPCs). Esse volume de material frequentemente se apresenta disperso em cadernos, planilhas, documentos avulsos e aplicativos distintos, tornando o processo de organização lento, suscetível a erros e pouco prático durante as sessões de jogo.

Diante desse cenário, identificou-se a necessidade de uma solução centralizada que permitisse ao mestre e aos jogadores acessar, gerenciar e atualizar as informações da campanha de maneira ágil, segura e multiplataforma. A ausência de uma ferramenta unificada para esse fim representa um gargalo direto à qualidade e à fluidez das sessões de RPG.

A **ShieldNote RPG-Box** surge como resposta a esse problema: uma API REST desenvolvida com o objetivo de centralizar o conteúdo de campanhas e aventuras em um único ambiente digital, eliminando o acúmulo de material físico e digital disperso, e oferecendo ao mestre uma "caixa" de administração completa para o gerenciamento de sua mesa.

---

## ✅ Requisitos e Casos de Uso

### Requisitos Funcionais

| ID | Requisito |
|---|---|
| RF01 | O sistema deve permitir o cadastro, autenticação e gerenciamento de usuários. |
| RF02 | O usuário autenticado deve poder criar e administrar sessões de jogo (Game Sessions). |
| RF03 | O sistema deve suportar a criação de personagens jogáveis (Player Characters) vinculados a um usuário e a uma sessão. |
| RF04 | O sistema deve permitir o cadastro e gerenciamento de NPCs (Personagens Não Jogáveis) associados a uma sessão. |
| RF05 | O sistema deve permitir o cadastro e consulta de monstros com seus respectivos atributos de combate. |
| RF06 | O sistema deve oferecer uma calculadora de dificuldade de encontros para auxílio do mestre. |
| RF07 | O sistema deve disponibilizar um conversor de moedas do universo do jogo. |
| RF08 | O sistema deve possuir um módulo de rolagem de dados com suporte a diferentes tipos e modificadores. |
| RF09 | O sistema deve permitir a criação de fichas de magias (Spellcasting) vinculadas a personagens. |
| RF10 | O sistema deve oferecer um bloco de anotações (Post-It) para anotações rápidas vinculadas à sessão. |
| RF11 | O sistema deve controlar o roster (lista de participantes) de cada sessão de jogo. |
| RF12 | O sistema deve permitir a exportação e importação de dados de personagem e campanha. |

### Requisitos Não Funcionais

| ID | Requisito |
|---|---|
| RNF01 | A API deve ser desenvolvida seguindo os princípios REST, com endpoints documentados. |
| RNF02 | O sistema deve garantir a segurança das informações por meio de autenticação baseada em tokens (JWT). |
| RNF03 | A arquitetura deve ser compatível com múltiplas plataformas de acesso (web e mobile). |
| RNF04 | O sistema deve ser capaz de propagar atualizações de fichas em tempo real entre os participantes de uma sessão. |
| RNF05 | O sistema deve ser escalável, suportando múltiplas sessões e usuários simultâneos. |
| RNF06 | O código deve seguir boas práticas de desenvolvimento orientado a objetos, com clara separação de responsabilidades. |

### Casos de Uso Principais

- **UC01 — Gerenciar Conta:** O usuário realiza cadastro, login e atualização de seus dados pessoais.
- **UC02 — Criar e Gerenciar Sessão:** O mestre cria uma sessão de jogo, define título, descrição e status, e vincula jogadores.
- **UC03 — Gerenciar Personagem:** O jogador cria, edita e acessa a ficha de seu personagem dentro de uma sessão.
- **UC04 — Gerenciar NPC/Monstro:** O mestre cadastra NPCs e monstros com seus atributos, associando-os à sessão ativa.
- **UC05 — Calcular Dificuldade de Encontro:** O mestre insere os monstros do encontro e obtém o nível de desafio calculado automaticamente.
- **UC06 — Converter Moedas:** O usuário converte valores entre as diferentes denominações monetárias do universo do jogo.
- **UC07 — Rolar Dados:** O sistema realiza rolagens de dados com modificadores, registrando o resultado e identificando críticos ou falhas.
- **UC08 — Gerenciar Magias:** O jogador registra e consulta as magias conhecidas e os espaços de magia disponíveis de seu personagem.
- **UC09 — Criar Anotações:** O mestre ou jogador cria notas rápidas (Post-Its) vinculadas à sessão, com visibilidade configurável.

---

## 🧩 Diagrama de Classes

> O diagrama a seguir representa as entidades do sistema, seus atributos, métodos e os relacionamentos entre elas, incluindo associações, composições e heranças.

<!-- 📌 INSERIR DIAGRAMA DE CLASSES AQUI -->

&nbsp;

---

## 🗄️ Diagrama Entidade-Relacionamento (DER)

> O DER apresentado abaixo descreve o modelo de dados relacional adotado para a persistência das informações da API, com as devidas chaves primárias (PK) e chaves estrangeiras (FK).

<!-- 📌 INSERIR DER AQUI -->

&nbsp;

---

## 🏗️ Projeto e Arquitetura

### Visão Geral

A ShieldNote RPG-Box é estruturada como uma API REST modular, organizada em torno de entidades de domínio bem definidas. A arquitetura separa as responsabilidades em camadas distintas — Controller, Service e Repository — seguindo o padrão MVC adaptado para APIs.

### Entidades do Domínio

| Entidade | Descrição |
|---|---|
| `USER` | Representa o usuário autenticado no sistema, seja ele mestre ou jogador. |
| `GAME_SESSION` | Representa uma campanha ou sessão de jogo, agregando todos os elementos narrativos e mecânicos. |
| `BASE_CHARACTER` | Entidade-base abstrata que centraliza os atributos compartilhados entre personagens jogáveis e NPCs. |
| `PLAYER_CHARACTER` | Especialização de `BASE_CHARACTER` para personagens controlados pelos jogadores. |
| `SESSION_ROSTER` | Gerencia o vínculo entre usuários/personagens e uma sessão, definindo papéis e ordem de iniciativa. |
| `NPC` | Personagem Não Jogável gerenciado pelo mestre, com atributos e habilidades específicas. |
| `MONSTER` | Entidade dedicada a criaturas de combate, contendo CR (Challenge Rating), atributos de batalha e tamanho. |
| `SPELLCASTING` | Ficha de magias vinculada a um personagem, com controle de espaços e frequência de uso. |
| `DIFFICULTY_CALCULATOR` | Módulo de cálculo de dificuldade de encontros com base nos monstros e no nível do grupo. |
| `DICE_ROLLER` | Módulo de rolagem de dados com suporte a modificadores, registro de críticos e falhas. |
| `MONEY_CONVERTER` | Módulo de conversão entre os diferentes tipos de moedas do universo do jogo. |
| `POST_IT` | Bloco de anotações rápidas vinculado à sessão, com campo de visibilidade (público/privado). |

### Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| **Back-end / API** | Java 21 + Spring Boot |
| **Segurança** | Spring Security + JSON Web Token (JWT) |
| **Persistência** | Spring Data JPA + Banco de Dados Relacional |
| **Tempo Real** | Firebase Realtime Database |
| **Documentação** | Swagger / OpenAPI 3.0 |

### Relacionamentos entre Entidades

- **`USER` → `GAME_SESSION`**: Um usuário pode ser mestre de múltiplas sessões *(1:N)*.
- **`GAME_SESSION` → `SESSION_ROSTER`**: Uma sessão contém múltiplos registros de roster *(1:N)*.
- **`SESSION_ROSTER` → `PLAYER_CHARACTER`**: Cada entrada no roster referencia um personagem do jogador *(N:1)*.
- **`BASE_CHARACTER` ← `PLAYER_CHARACTER` / `NPC`**: `PLAYER_CHARACTER` e `NPC` herdam de `BASE_CHARACTER` por composição de atributos compartilhados.
- **`PLAYER_CHARACTER` → `SPELLCASTING`**: Um personagem possui uma ficha de magias associada *(1:1)*.
- **`GAME_SESSION` → `NPC`, `MONSTER`, `POST_IT`, `DIFFICULTY_CALCULATOR`**: A sessão agrega todos esses elementos de campanha *(1:N)*.
- **`MONSTER` → `BASE_CHARACTER`**: O monstro referencia uma entidade-base de personagem para atributos gerais *(1:1)*.

---

## ⚙️ Implementação

### Módulo `USER` *(Khauan)*

Responsável pelo ciclo completo de gerenciamento de usuários. Implementa os endpoints de registro, autenticação (login com geração de JWT e refresh token), atualização de dados e controle de perfil de acesso (mestre ou jogador). A segurança é aplicada via filtros do Spring Security, garantindo que recursos protegidos exijam token válido.

### Módulo `DIFFICULTY_CALCULATOR` *(Vinícius)*

Implementa a lógica de cálculo automático da dificuldade de encontros de combate. O módulo recebe a lista de monstros do encontro e o nível médio do grupo de jogadores, aplicando os multiplicadores de grupo definidos pelo sistema de regras para classificar o encontro como Fácil, Médio, Difícil ou Mortal. O resultado é persistido para consulta histórica pelo mestre.

### Módulo `Campaign` *(Vinícius)*

Entidade central da API. Gerencia a criação e o ciclo de vida das sessões de jogo, incluindo título, descrição, status (ativa/encerrada) e módulo de rollplay. Serve como ponto de agregação para NPCs, Monstros, Rostos de Sessão, Post-Its e Calculadoras de Dificuldade.

### Módulo `BASE_CHARACTER` *(João Victor)*

Define a estrutura base de atributos compartilhada por `PLAYER_CHARACTER` e `NPC`, centralizando campos como classe, subclasse, raça, alinhamento e nível. Evita duplicação de lógica ao concentrar operações comuns de atributos e condições em um único ponto de herança/composição.

### Módulo `PLAYER_CHARACTER` *(João Victor)*

Especialização de `BASE_CHARACTER` para personagens controlados pelos jogadores. Adiciona atributos exclusivos como pontos de vida (com bônus de perfil), nível de magia e lógica de progressão. Expõe endpoints para atualização dinâmica da ficha durante a sessão, com propagação em tempo real via Firebase.

### Módulo `NPC` *(João Victor)*

Permite ao mestre cadastrar e gerenciar Personagens Não Jogáveis com atributos completos, alinhamento, habilidades e classes. Vinculado a uma sessão e associado a um `BASE_CHARACTER`, o módulo suporta operações de atualização de status durante encontros.

### Módulo `MONSTER` *(João Victor)*

Entidade dedicada às criaturas de combate. Armazena tipo, subtipo, tamanho, velocidade, atributos de combate (Pontos de Vida Brutos e Média), alinhamento e CR (Challenge Rating). Utilizado diretamente pelo módulo `DIFFICULTY_CALCULATOR` para os cálculos de encontro.

### Módulo `SPELLCASTING` *(Vinícius)*

Ficha de magias associada a um `PLAYER_CHARACTER`. Controla o conjunto de magias conhecidas, os espaços de magia disponíveis por nível, a frequência de uso e as magias preparadas para a sessão corrente. Expõe endpoints para consumo e recuperação de espaços de magia.

---

## 📝 Conclusão

O desenvolvimento da **ShieldNote RPG-Box** representa uma resposta concreta ao problema de fragmentação e sobrecarga organizacional enfrentado por mestres de campanhas de RPG. Ao centralizar fichas de personagens, controle de encontros, gerenciamento de NPCs e monstros, anotações de sessão e ferramentas auxiliares em uma única API, o projeto viabiliza uma experiência de jogo mais fluida e organizada para todos os participantes da mesa.

Do ponto de vista técnico, a adoção do Spring Boot como base do back-end garante um ambiente maduro para a construção de APIs REST seguras e escaláveis. A integração com Firebase para propagação de atualizações em tempo real endereça diretamente o requisito de sincronização das fichas entre mestre e jogadores durante a sessão — um dos diferenciais mais relevantes do produto.

É importante ressaltar, contudo, que o escopo original do projeto é ambicioso. A estratégia adotada pela equipe priorizou corretamente a entrega de um **Produto Mínimo Viável (MVP)**, garantindo o funcionamento robusto do núcleo da aplicação — autenticação, sessões, personagens e encontros — antes de avançar para funcionalidades secundárias como conversão monetária e exportação de arquivos. Essa abordagem iterativa reduz riscos de atraso e permite validar as hipóteses centrais do produto com usuários reais.

Com a modularização clara das responsabilidades entre os membros da equipe e a arquitetura em camadas adotada, o projeto possui uma base sólida para evoluir em direção às funcionalidades mais complexas previstas no backlog, como o suporte multiplataforma completo, as anotações flutuantes e a importação/exportação de conteúdo em PDF.

---

## ℹ️ Informações Adicionais

### Pré-requisitos

- Java 21+
- Maven 3.8+
- Banco de Dados Relacional (PostgreSQL recomendado)
- Conta Firebase com Realtime Database habilitado

### Como Executar Localmente

```bash
# 1. Clone o repositório
git clone https://github.com/<seu-usuario>/shieldnote-rpg-box.git
cd shieldnote-rpg-box

# 2. Configure as variáveis de ambiente em src/main/resources/application.properties
#    (banco de dados, credenciais Firebase, chave JWT)

# 3. Compile e execute
mvn spring-boot:run
```

### Estrutura de Pacotes

```
src/
└── main/
    └── java/
        └── com.shieldnote.rpgbox/
            ├── assembler/       # Camada de geração de links
            ├── config/          # Configurações de segurança e Firebase
            ├── controller/      # Camada de entrada (endpoints REST)
            ├── service/         # Camada de regras de negócio
            ├── repository/      # Camada de acesso a dados (JPA)
            ├── model/           # Entidades de domínio
                └──vo/           # Value Objects (objetos imutáveis que representam conceitos do domínio)
            └── dto/             # Objetos de transferência de dados
                ├──request/      # Camada de entrada
                └──response/     # Camada de saída
```

### Endpoints Principais

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/auth/register` | Cadastro de novo usuário |
| `POST` | `/auth/login` | Autenticação e geração de token |
| `GET/POST` | `/sessions` | Listar/criar sessões de jogo |
| `GET/POST` | `/characters` | Listar/criar personagens |
| `GET/POST` | `/sessions/{id}/npcs` | Gerenciar NPCs de uma sessão |
| `GET/POST` | `/sessions/{id}/monsters` | Gerenciar monstros de uma sessão |
| `POST` | `/tools/difficulty` | Calcular dificuldade de encontro |
| `POST` | `/tools/dice` | Realizar rolagem de dados |
| `POST` | `/tools/currency` | Converter moedas do jogo |
| `GET/POST` | `/sessions/{id}/postits` | Gerenciar anotações da sessão |

### Glossário

| Termo | Definição |
|---|---|
| **RPG** | Role-Playing Game — jogo de interpretação de papéis em que jogadores constroem narrativas colaborativas. |
| **Mestre** | Jogador responsável por narrar a história, controlar NPCs e árbitrar as regras do jogo. |
| **Ficha** | Documento (físico ou digital) que registra todos os atributos, habilidades e histórico de um personagem. |
| **NPC** | Non-Playable Character — personagem controlado pelo mestre, não por um jogador. |
| **CR** | Challenge Rating — indicador numérico que representa o nível de ameaça de um monstro. |
| **JWT** | JSON Web Token — padrão de tokens de autenticação segura e sem estado. |
| **MVP** | Minimum Viable Product — versão mínima funcional do produto, com as funcionalidades essenciais implementadas. |

---

<div align="center">
  <sub>Desenvolvido com ☕ e muitos d20s.</sub>
</div>
