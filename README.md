# 🛡️ ShieldNote RPG-Box

> API REST de gerenciamento de campanhas e sessões de RPG de Mesa

**Instituição:** Instituto Federal Goiano — Campus Urutaí (IFG)  
**Disciplina:** Programação Web Ⅱ

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
6. [Implementação](#️-implementação)
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
| RF02 | O usuário autenticado deve poder criar e administrar campanhas de jogo. |
| RF03 | O sistema deve suportar a criação de personagens jogáveis (`PlayerCharacter`) vinculados a um usuário e a uma campanha. |
| RF04 | O sistema deve permitir o cadastro e gerenciamento de NPCs (Personagens Não Jogáveis) associados a uma campanha. |
| RF05 | O sistema deve permitir o cadastro e consulta de monstros com seus respectivos atributos de combate e Challenge Rating (CR). |
| RF06 | O sistema deve oferecer uma calculadora de dificuldade de encontros para auxílio do mestre, classificando como Fácil, Médio, Difícil ou Mortal. |
| RF07 | O sistema deve possuir um módulo de gerenciamento de magias (`Spell`, `SpellCasting`, `SpellSlot`) vinculado a personagens. |
| RF08 | O sistema deve permitir a criação de anotações (`Notes`) vinculadas a campanhas, com categorias e visibilidade configuráveis. |
| RF09 | O sistema deve controlar os espaços de magia disponíveis por nível para cada personagem conjurador. |
| RF10 | O sistema deve expor documentação interativa dos endpoints via Swagger/OpenAPI. |

### Requisitos Não Funcionais

| ID | Requisito |
|---|---|
| RNF01 | A API deve ser desenvolvida seguindo os princípios REST, com endpoints documentados via OpenAPI 3.0. |
| RNF02 | A arquitetura deve ser compatível com múltiplas plataformas de acesso (web e mobile). |
| RNF03 | O sistema deve ser escalável, suportando múltiplas campanhas e usuários simultâneos. |
| RNF04 | O código deve seguir boas práticas de desenvolvimento orientado a objetos, com clara separação de responsabilidades (Controller → Service → Repository). |
| RNF05 | O sistema deve utilizar cache (Caffeine) para otimizar consultas frequentes. |
| RNF06 | A persistência deve ser gerenciada por Spring Data JPA com banco de dados relacional (MySQL em produção, H2 em testes). |
| RNF07 | A API deve seguir o padrão HATEOAS para navegação hipermídia entre recursos. |

### Casos de Uso Principais

- **UC01 — Gerenciar Conta:** O usuário realiza cadastro, login e atualização de seus dados pessoais.
- **UC02 — Criar e Gerenciar Campanha:** O mestre cria uma campanha, define título, descrição, sistema e status (ATIVA / PAUSADA / FINALIZADA), e a gerencia ao longo do tempo.
- **UC03 — Gerenciar Personagem:** O jogador cria, edita e acessa a ficha de seu personagem (`PlayerCharacter`) dentro de uma campanha, incluindo atributos, proficiências e dinheiro.
- **UC04 — Gerenciar NPC:** O mestre cadastra NPCs com atributos completos, ocupação, hostilidade e recompensas, associando-os a uma campanha.
- **UC05 — Gerenciar Monstro:** O mestre cadastra criaturas de combate com tipo, subtipo, tamanho, CR e lista de ações.
- **UC06 — Calcular Dificuldade de Encontro:** O mestre informa os níveis dos jogadores e o XP dos monstros do encontro, recebendo a classificação de dificuldade calculada automaticamente segundo as regras do D&D 5e.
- **UC07 — Gerenciar Magias:** O jogador registra a ficha de conjuração (`SpellCasting`) do seu personagem, consulta as magias conhecidas, conjura magias gastando slots e recupera slots após descanso.
- **UC08 — Criar Anotações:** O mestre ou jogador cria notas (`Notes`) vinculadas a uma campanha, com categoria (`PostItCat`) e visibilidade configurável (MESTRE / JOGADOR).

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

A ShieldNote RPG-Box é estruturada como uma API REST modular, organizada em torno de entidades de domínio bem definidas. A arquitetura separa as responsabilidades em camadas distintas — **Controller**, **Service** e **Repository** — seguindo o padrão MVC adaptado para APIs, com a camada de domínio composta pelos modelos JPA e pelos Value Objects (`vo`).

### Entidades do Domínio

| Entidade | Descrição |
|---|---|
| `User` | Representa o usuário autenticado no sistema, seja ele mestre ou jogador. |
| `Campaign` | Entidade central que agrega todos os elementos da sessão de jogo. Funciona como raiz do agregado, gerenciando o ciclo de vida das `Notes` por composição. |
| `Notes` | Anotações do tipo post-it vinculadas a uma campanha. Possuem categoria (`PostItCat`) e visibilidade (`RoleEnum`). |
| `Character` | Entidade-base JPA com herança `SINGLE_TABLE`. Centraliza atributos compartilhados por `PlayerCharacter`, `NPC` e `Monster`. |
| `PlayerCharacter` | Especialização de `Character` para personagens controlados pelos jogadores. Adiciona raça, classe, subclasse, nível e dinheiro. |
| `NPC` | Especialização de `Character` para Personagens Não Jogáveis. Adiciona ocupação, descrição, hostilidade e recompensa. |
| `Monster` | Especialização de `Character` para criaturas de combate. Adiciona tamanho, tipo, CR (Challenge Rating), XP base e lista de ações. |
| `Spell` | Entidade independente de magias, com nível, tempo de conjuração, alcance, duração, escola e componentes. |
| `SpellCasting` | Ficha de conjuração vinculada a um personagem por `characterId`. Controla magias conhecidas e agrega `SpellSlot`s. |
| `SpellSlot` | Espaço de magia por nível. Possui quantidade atual e máxima, com método `gastarSlot()` e `recuperar()`. |
| `Proficiencies` | Value Object embarcado em `Character`. Guarda proficiências em perícias (`Skill`) e testes de resistência (`Ability`). |
| `Atributo` | Value Object embarcado em `Character`. Representa os seis atributos do D&D (STR, DEX, CON, INT, WIS, CHA). |
| `EncounterCalculator` | Service de cálculo automático de dificuldade de encontros segundo as regras do D&D 5e. |

### Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| **Back-end / API** | Java 21 + Spring Boot 3.2.5 |
| **Persistência** | Spring Data JPA + MySQL (produção) / H2 (testes) |
| **Mapeamento** | MapStruct 1.6.3 |
| **Cache** | Spring Cache + Caffeine |
| **HATEOAS** | Spring HATEOAS |
| **Documentação** | Springdoc OpenAPI 3.0 (Swagger UI) |
| **Utilitários** | Lombok |
| **Testes** | Spring Boot Test + JUnit 5 |

### Relacionamentos entre Entidades

- **`User` → `Campaign`**: Um usuário (mestre) pode criar múltiplas campanhas *(1:N)*. A referência é mantida como `mestreId` (UUID) na campanha.
- **`Campaign` ◆→ `Notes`**: Uma campanha contém múltiplas notas *(1:N)*. Relacionamento de composição — `Notes` é destruída ao excluir a campanha (`CascadeType.ALL`, `orphanRemoval = true`).
- **`Character` ← `PlayerCharacter` / `NPC` / `Monster`**: As três entidades herdam de `Character` via estratégia `SINGLE_TABLE` (JPA), compartilhando a tabela `tb_character` e diferenciadas pela coluna `dtype`.
- **`Character` ◆→ `Atributo` / `Proficiencies`**: Composição via `@Embedded`. Os value objects não possuem existência independente.
- **`SpellCasting` ◆→ `SpellSlot`**: Uma ficha de conjuração agrega seus slots de magia *(1:N)*, com `orphanRemoval = true`.
- **`SpellCasting` → `Spell`**: Associação por referência de ID (`magiasConhecidas` armazena UUIDs de `Spell` como strings).
- **`Monster` → `EncounterCalculator`**: Dependência de uso — o serviço consome `challengeRating` e `xpBase` dos monstros para o cálculo de dificuldade.

---

## ⚙️ Implementação

### Módulo `User` *(Khauan)*

Responsável pelo ciclo completo de gerenciamento de usuários. Implementa os endpoints de registro (`POST /users`), consulta (`GET /users/{id}`) e atualização de dados. A entidade `User` utiliza o callback `@PrePersist` para registrar automaticamente o timestamp de criação. As respostas seguem o padrão HATEOAS com links de navegação gerados pelo `UserModelAssembler`.

### Módulo `Campaign` *(Vinícius)*

Entidade central da API. Gerencia o ciclo de vida das campanhas incluindo título, descrição, sistema de regras utilizado, status (`ATIVA`, `PAUSADA`, `FINALIZADA`) e capacidade de jogadores. A classe implementa o padrão **Aggregate Root**: os métodos de negócio `pausar()`, `reativar()` e `encerrar()` encapsulam as transições de estado, e o relacionamento com `Notes` é gerenciado por composição JPA com `CascadeType.ALL`. O `CampaignModelAssembler` produz respostas HATEOAS com links contextuais.

### Módulo `Notes` *(Vinícius)*

Implementa as anotações vinculadas a campanhas. Cada nota possui categoria (`PostItCat`) e visibilidade por papel (`RoleEnum`), permitindo ao mestre criar notas exclusivas ou compartilhadas com os jogadores. Os métodos `atualizarConteudo()` e `alternarFixacao()` encapsulam regras de negócio do agregado. O `NotesRepository` expõe queries derivadas por campanha e por sessão.

### Módulo `Character` / `PlayerCharacter` / `NPC` / `Monster` *(João Victor)*

Implementa a hierarquia de personagens utilizando a estratégia de herança `SINGLE_TABLE` do JPA (`@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`). A classe-base `Character` centraliza atributos como pontos de vida, classe de armadura, velocidade e alinhamento, além de dois value objects embarcados: `Atributo` (seis atributos base do D&D) e `Proficiencies` (conjunto de perícias e testes de resistência). O método `adjustHitPoints()` garante que os HP correntes permaneçam dentro dos limites válidos.

`PlayerCharacter` adiciona raça, classe, subclasse, nível e dinheiro. `NPC` adiciona ocupação, hostilidade e recompensa. `Monster` adiciona tamanho (`SizeEnum`), tipo, CR, XP base e lista de ações — usado diretamente pelo `EncounterCalculator`.

### Módulo `EncounterCalculator` *(Vinícius)*

Implementa a lógica de cálculo de dificuldade de encontros segundo as regras oficiais do D&D 5e. O serviço recebe os níveis dos jogadores e o XP dos monstros, aplica os limiares de XP por nível e os multiplicadores de quantidade de monstros, e retorna a dificuldade classificada (FÁCIL / MÉDIO / DIFÍCIL / MORTAL) junto ao XP ajustado e aos limiares do grupo. A tabela `XP_THRESHOLDS` está embutida como constante estática, cobrindo os níveis 1 a 5.

### Módulo `Spell` *(Khauan)*

Entidade independente que armazena as informações de cada magia: nome, descrição, nível, tempo de conjuração, alcance, duração, escola e componentes. O método de negócio `getIndiceComplexidade()` retorna um índice proporcional ao nível da magia. Os endpoints de `SpellController` expõem operações CRUD completas com filtragem por escola e nível, e respostas HATEOAS via `SpellModelAssembler`.

### Módulo `SpellCasting` *(Khauan)*

Ficha de conjuração vinculada a um `PlayerCharacter` por `characterId`. Controla o atributo de conjuração, CD de magias e bônus de ataque mágico. A lista `magiasConhecidas` armazena os IDs das magias aprendidas pelo personagem. O método `conjurar()` valida se a magia é conhecida e consome um slot do nível solicitado, enquanto `recuperarSlots()` restaura todos os slots após descanso longo.

### Módulo `SpellSlot` *(Khauan)*

Controla os espaços de magia por nível disponíveis em um `SpellCasting`. Cada `SpellSlot` possui nível, quantidade atual e quantidade máxima. Os métodos `gastarSlot()` e `recuperar()` encapsulam o controle de uso, garantindo que não seja possível conjurar sem slot disponível. O relacionamento com `SpellCasting` é de agregação com `orphanRemoval = true`.

---

## 📝 Conclusão

O desenvolvimento da **ShieldNote RPG-Box** representa uma resposta concreta ao problema de fragmentação e sobrecarga organizacional enfrentado por mestres de campanhas de RPG. Ao centralizar fichas de personagens, controle de encontros, gerenciamento de NPCs e monstros, anotações de sessão e controle de magias em uma única API, o projeto viabiliza uma experiência de jogo mais fluida e organizada para todos os participantes da mesa.

Do ponto de vista técnico, a adoção da estratégia de herança `SINGLE_TABLE` para a hierarquia de personagens demonstra a aplicação prática de conceitos de orientação a objetos — herança, polimorfismo e encapsulamento — integrados a um mapeamento objeto-relacional real. O padrão **Aggregate Root** aplicado em `Campaign` e `SpellCasting` evidencia a separação clara de responsabilidades e o controle transacional do domínio.

A arquitetura em camadas (Controller → Service → Repository), o uso de Value Objects (`Atributo`, `Proficiencies`) e os padrões HATEOAS e OpenAPI garantem uma base sólida, extensível e bem documentada. A cobertura por testes unitários e de integração (JUnit 5 + Spring Boot Test) contribui para a confiabilidade do MVP entregue.

É importante destacar que o escopo do projeto foi adequado à estratégia de **Produto Mínimo Viável (MVP)**, priorizando o funcionamento robusto do núcleo da aplicação — campanhas, personagens, hierarquia de entidades e controle de magias — antes de avançar para funcionalidades secundárias. Essa abordagem iterativa reduz riscos e permite validar as hipóteses centrais com usuários reais.

---

## ℹ️ Informações Adicionais

### Pré-requisitos

- Java 21+
- Maven 3.8+
- MySQL 8+ (ou H2 para execução em modo de testes)

### Como Executar Localmente

```bash
# 1. Clone o repositório
git clone https://github.com/ifg-urt/shieldnote-rpg-box.git
cd shieldnote-rpg-box

# 2. Configure as variáveis de ambiente em src/main/resources/application.properties
#    (URL do banco de dados, usuário e senha)

# 3. Compile e execute
mvn spring-boot:run
```

A documentação Swagger estará disponível em: `http://localhost:8080/swagger-ui.html`

### Estrutura de Pacotes

```
src/
└── main/
    └── java/
        └── br.ifg.urt.shieldnoterpgbox/
            ├── assembler/       # Camada de geração de links HATEOAS
            ├── config/          # Configurações (Cache, OpenAPI)
            ├── controller/      # Camada de entrada (endpoints REST)
            ├── dto/
            │   ├── request/     # DTOs de entrada
            │   └── response/    # DTOs de saída
            ├── enums/           # Enumerações do domínio (StatusEnum, SizeEnum, ...)
            ├── exception/       # Tratamento global de exceções
            ├── mapper/          # Conversões entre entidades e DTOs (MapStruct)
            ├── model/           # Entidades de domínio JPA
            │   └── vo/          # Value Objects (Atributo, Alignment, Proficiencies, ...)
            ├── repository/      # Camada de acesso a dados (Spring Data JPA)
            └── service/         # Camada de regras de negócio
```

### Endpoints Principais

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/users` | Cadastro de novo usuário |
| `GET` | `/users/{id}` | Consultar usuário por ID |
| `GET/POST` | `/campaigns` | Listar / criar campanhas |
| `PATCH` | `/campaigns/{id}/pausar` | Pausar campanha |
| `PATCH` | `/campaigns/{id}/encerrar` | Encerrar campanha |
| `GET/POST` | `/campaigns/{id}/notes` | Listar / criar notas de uma campanha |
| `GET/POST` | `/characters` | Listar / criar personagens (`PlayerCharacter`) |
| `GET/POST` | `/npcs` | Listar / criar NPCs |
| `GET/POST` | `/monsters` | Listar / criar monstros |
| `POST` | `/encounter/calcular` | Calcular dificuldade de encontro |
| `GET/POST` | `/spells` | Listar / criar magias |
| `GET/POST` | `/spellcastings` | Listar / criar fichas de conjuração |
| `POST` | `/spellcastings/{id}/conjurar` | Conjurar magia (consome slot) |
| `POST` | `/spellcastings/{id}/recuperar` | Recuperar todos os slots de magia |
| `GET/POST` | `/spellslots` | Listar / criar slots de magia |

### Enumerações do Domínio

| Enum | Valores |
|---|---|
| `StatusEnum` | `ATIVA`, `PAUSADA`, `FINALIZADA` |
| `SizeEnum` | `TINY`, `SMALL`, `MEDIUM`, `LARGE`, `HUGE`, `GARGANTUAN` |
| `RoleEnum` | `MESTRE`, `JOGADOR` |
| `PostItCat` | Categorias de anotação (ex.: GERAL, SEGREDO, MISSÃO) |
| `Dificuldade` | `FACIL`, `MEDIO`, `DIFICIL`, `MORTAL` |

### Glossário

| Termo | Definição |
|---|---|
| **RPG** | Role-Playing Game — jogo de interpretação de papéis em que jogadores constroem narrativas colaborativas. |
| **Mestre** | Jogador responsável por narrar a história, controlar NPCs e árbitrar as regras do jogo. |
| **Ficha** | Documento (físico ou digital) que registra todos os atributos, habilidades e histórico de um personagem. |
| **NPC** | Non-Playable Character — personagem controlado pelo mestre, não por um jogador. |
| **CR** | Challenge Rating — indicador numérico que representa o nível de ameaça de um monstro. |
| **Slot de Magia** | Recurso consumível que permite a um personagem conjurar magias de determinado nível. |
| **HATEOAS** | Hypermedia As The Engine Of Application State — padrão REST que inclui links de navegação nas respostas. |
| **Aggregate Root** | Padrão DDD em que uma entidade central controla o ciclo de vida das entidades do seu agregado. |
| **Value Object** | Objeto sem identidade própria, definido apenas por seus atributos (ex.: `Atributo`, `Proficiencies`). |
| **MVP** | Minimum Viable Product — versão mínima funcional do produto, com as funcionalidades essenciais implementadas. |
| **SINGLE_TABLE** | Estratégia JPA de herança que mapeia toda a hierarquia de classes em uma única tabela do banco de dados. |

---

<div align="center">
  <sub>Desenvolvido com ☕ e muitos d20s — IFG Campus Uruaçu, 2025.</sub>
</div>
