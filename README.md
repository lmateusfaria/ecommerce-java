# ALUNOS:
## Luis Mateus dos Reis Faria
## Kaio Monteiro de Oliveira
## Matheus Henrique Garcia Queiroz

# Sistema de E-commerce: Gerenciamento de Pedidos com Padrões de Design

## Visão Geral do Projeto

Este projeto demonstra a construção de um sistema de e-commerce robusto e flexível para gerenciamento de pedidos, utilizando Java 17 e o framework Spring Boot. O foco principal reside na aplicação estratégica de padrões de design (State, Strategy e Factory Method) para garantir alta manutenibilidade, extensibilidade e clareza arquitetural.

## Tecnologias Chave

- **Java 17**: Linguagem de programação moderna e performática.
- **Spring Boot 3.2.3**: Framework líder para desenvolvimento de aplicações Java, facilitando a configuração e o deploy.
- **Spring Data JPA**: Simplifica a camada de persistência de dados, abstraindo complexidades do JDBC e ORM.
- **H2 Database**: Banco de dados em memória, ideal para desenvolvimento e testes rápidos.
- **PostgreSQL**: Banco de dados relacional robusto, recomendado para ambientes de produção.
- **SpringDoc OpenAPI**: Geração automática de documentação de API (Swagger UI), promovendo a colaboração e o consumo da API.
- **Maven**: Ferramenta de automação de build e gerenciamento de dependências, garantindo consistência no ambiente de desenvolvimento.

## Arquitetura e Padrões de Design

A arquitetura do sistema é modular e orientada a padrões, promovendo um design limpo e adaptável a futuras mudanças. Os seguintes padrões de design foram aplicados:

### 1. Padrão State (Comportamental)

**Propósito**: Gerenciar as transições de estado de um pedido de forma controlada e encapsulada.

**Problema Resolvido**: A complexidade de lidar com as diversas regras de negócio para cada status de pedido (e.g., um pedido pago não pode ser pago novamente, um pedido cancelado não pode mudar de status) é mitigada. O padrão State permite que o comportamento de um objeto mude quando seu estado interno muda, tornando o objeto mais fácil de manter e estender.

**Implementação**: 
- **`PedidoState` (Interface)**: Define as operações comuns a todos os estados (pagar, cancelar, enviar).
- **Estados Concretos**: 
  - `AguardandoPagamentoState`: Estado inicial, permite transições para `PAGO` ou `CANCELADO`.
  - `PagoState`: Permite transições para `ENVIADO` ou `CANCELADO`.
  - `EnviadoState`: Estado final, não permite mais transições.
  - `CanceladoState`: Estado final, não permite mais transições.
- **`PedidoStateManager` (Contexto)**: Atua como a interface para o cliente, delegando as chamadas para o objeto de estado atual do `Pedido`.

**Benefícios Arquiteturais**:
- **Clareza e Organização**: A lógica de cada estado é isolada em sua própria classe, facilitando a compreensão e a manutenção.
- **Segurança das Transições**: Garante que apenas transições de estado válidas sejam permitidas, prevenindo erros de negócio.
- **Extensibilidade**: A adição de novos estados ou regras de transição é simplificada, exigindo a criação de novas classes de estado sem modificar o código existente (Princípio Open/Closed).

### 2. Padrão Strategy (Comportamental)

**Propósito**: Definir uma família de algoritmos, encapsulá-los e torná-los intercambiáveis.

**Problema Resolvido**: O cálculo do frete pode variar significativamente (e.g., frete terrestre, aéreo, marítimo, por peso, por distância). O padrão Strategy permite que o algoritmo de cálculo de frete seja selecionado em tempo de execução, sem a necessidade de múltiplas condicionais (`if/else` ou `switch`) no código principal.

**Implementação**: 
- **`FreteStrategy` (Interface)**: Declara o método `calcularFrete(BigDecimal valorPedido)` e métodos para obter o tipo e descrição do frete.
- **Estratégias Concretas**: 
  - `FreteTerrestreStrategy`: Implementa o cálculo de frete como 5% do valor do pedido.
  - `FreteAereoStrategy`: Implementa o cálculo de frete como 10% do valor do pedido.
- **`FreteCalculator` (Contexto)**: Contém uma referência à `FreteStrategy` e delega a ela a execução do cálculo.

**Benefícios Arquiteturais**:
- **Flexibilidade**: Permite a fácil adição de novas modalidades de frete sem alterar o código cliente (`FreteCalculator`).
- **Reusabilidade**: As estratégias de frete podem ser reutilizadas em diferentes contextos da aplicação.
- **Testabilidade**: Cada estratégia pode ser testada isoladamente, garantindo a correção dos algoritmos de cálculo.

### 3. Padrão Factory Method (Criacional)

**Propósito**: Fornecer uma interface para criar objetos em uma superclasse, mas permitir que as subclasses alterem o tipo de objetos que serão criados.

**Problema Resolvido**: A criação de instâncias de `FreteStrategy` (e futuras estratégias) é centralizada e desacoplada do código que as utiliza. Isso evita a dependência direta do código cliente em relação às classes concretas de estratégia, promovendo um acoplamento fraco.

**Implementação**: 
- **`FreteStrategyFactory` (Classe Factory)**: Contém métodos estáticos para criar instâncias de `FreteStrategy` com base em um tipo de frete fornecido (e.g., "TERRESTRE", "AEREO"). Também oferece métodos para listar os tipos de frete disponíveis e validar se um tipo é suportado.

**Benefícios Arquiteturais**:
- **Desacoplamento**: O código cliente não precisa conhecer as classes concretas de `FreteStrategy`, apenas a interface.
- **Centralização da Criação**: Facilita a manutenção e a adição de novas estratégias, pois a lógica de criação está em um único local.
- **Controle e Validação**: A fábrica pode incluir lógica para validar os tipos de objetos que podem ser criados, garantindo a integridade do sistema.

## Estrutura do Projeto

O projeto segue uma estrutura de pacotes organizada para refletir a separação de responsabilidades e a aplicação dos padrões de design:

```
src/main/java/com/ecommerce/system/
├── domain/           # Entidades JPA (Pedido, Cliente, Produto, ItemPedido)
├── state/            # Implementação do padrão State (interfaces e estados concretos)
├── strategy/         # Implementação do padrão Strategy (interfaces e estratégias concretas)
├── factory/          # Implementação do padrão Factory Method
├── repository/       # Repositórios Spring Data JPA para acesso a dados
├── service/          # Camada de serviços com a lógica de negócio
├── controller/       # Controladores REST para exposição da API
├── dto/              # Data Transfer Objects para comunicação entre camadas
└── config/           # Classes de configuração da aplicação (e.g., H2, OpenAPI)
```

## Entidades do Sistema

### `Pedido`
Representa um pedido no sistema, incluindo seu status, valor total e informações de frete.
- **Atributos**: `id`, `numeroPedido`, `valorTotal`, `dataCriacao`, `status`, `valorFrete`, `tipoFrete`.
- **Relacionamentos**: `ManyToOne` com `Cliente`, `OneToMany` com `ItemPedido`.

### `Cliente`
Representa um cliente que realiza pedidos.
- **Atributos**: `id`, `nome`, `email`, `telefone`, `endereco`.
- **Relacionamentos**: `OneToMany` com `Pedido`.

### `Produto`
Representa um item disponível para compra no e-commerce.
- **Atributos**: `id`, `nome`, `descricao`, `preco`, `estoque`.
- **Relacionamentos**: `OneToMany` com `ItemPedido`.

### `ItemPedido`
Representa um item específico dentro de um pedido, associando um produto a uma quantidade.
- **Atributos**: `id`, `quantidade`, `precoUnitario`, `subtotal`.
- **Relacionamentos**: `ManyToOne` com `Pedido` e `Produto`.

## Fluxo de Estados do Pedido

O ciclo de vida de um pedido é gerenciado pelo padrão State, garantindo transições controladas:

1. **`AGUARDANDO_PAGAMENTO`** (Estado inicial)
   - Pode transitar para **`PAGO`** (se o pagamento for bem-sucedido).
   - Pode transitar para **`CANCELADO`** (se o cliente cancelar ou o pagamento falhar).

2. **`PAGO`**
   - Pode transitar para **`ENVIADO`** (após o processamento e despacho).
   - Pode transitar para **`CANCELADO`** (se o cliente cancelar antes do envio).

3. **`ENVIADO`** (Estado final)
   - Nenhuma transição adicional é permitida.

4. **`CANCELADO`** (Estado final)
   - Nenhuma transição adicional é permitida.

## APIs REST Disponíveis

O sistema expõe uma API RESTful para interação com os recursos de pedidos e frete. A documentação completa está disponível via Swagger UI.

### Endpoints de Pedidos
- `POST /api/pedidos`: Cria um novo pedido no sistema.
- `GET /api/pedidos/{id}`: Recupera os detalhes de um pedido específico pelo seu ID.
- `GET /api/pedidos`: Lista todos os pedidos registrados.
- `PUT /api/pedidos/{id}/pagar`: Altera o status de um pedido para `PAGO`.
- `PUT /api/pedidos/{id}/cancelar`: Altera o status de um pedido para `CANCELADO`.
- `PUT /api/pedidos/{id}/enviar`: Altera o status de um pedido para `ENVIADO`.

### Endpoints de Frete
- `GET /api/pedidos/frete/calcular`: Calcula o valor do frete para um dado valor de pedido e tipo de frete.
- `GET /api/pedidos/frete/tipos`: Retorna uma lista dos tipos de frete disponíveis no sistema.

## Como Executar o Projeto

Para configurar e executar o projeto localmente, siga os passos abaixo:

### Pré-requisitos
- **Java Development Kit (JDK) 17** ou superior.
- **Apache Maven 3.6+**.

### Passos para Execução

1. **Clone o Repositório**:
   ```bash
   git clone <url-do-repositorio>
   cd ecommerce-project
   ```

2. **Compile o Projeto**:
   Certifique-se de que a variável de ambiente `JAVA_HOME` esteja configurada para o seu JDK 17.
   ```bash
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 # Exemplo para Linux/macOS
   mvn clean compile
   ```

3. **Execute a Aplicação Spring Boot**:
   ```bash
   mvn spring-boot:run
   ```
   A aplicação será iniciada e estará acessível em `http://localhost:8080`.

4. **Acesse a Documentação da API (Swagger UI)**:
   Abra seu navegador e acesse: `http://localhost:8080/swagger-ui.html`
   A especificação OpenAPI também está disponível em: `http://localhost:8080/api-docs`

5. **Acesse o Console H2 (Apenas para Desenvolvimento)**:
   Para inspecionar o banco de dados em memória durante o desenvolvimento, acesse:
   - **URL**: `http://localhost:8080/h2-console`
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **Username**: `sa`
   - **Password**: (deixe em branco)

## Dados de Teste Pré-carregados

O sistema inicializa automaticamente com um conjunto de dados de teste para facilitar a exploração da API:

### Clientes
- **ID 1**: João Silva (`joao@email.com`)
- **ID 2**: Maria Santos (`maria@email.com`)
- **ID 3**: Pedro Oliveira (`pedro@email.com`)

### Produtos
- **ID 1**: Notebook Dell - R$ 2.500,00 (Estoque: 10)
- **ID 2**: Mouse Logitech - R$ 350,00 (Estoque: 25)
- **ID 3**: Teclado Mecânico - R$ 450,00 (Estoque: 15)
- **ID 4**: Monitor 24" - R$ 800,00 (Estoque: 8)
- **ID 5**: Smartphone Samsung - R$ 3.200,00 (Estoque: 12)

## Exemplos de Uso da API (cURL)

### Criar um Novo Pedido
```bash
curl -X POST "http://localhost:8080/api/pedidos" \
  -H "Content-Type: application/json" \
  -d 
```

### Calcular Frete
```bash
curl -X GET "http://localhost:8080/api/pedidos/frete/calcular?valorPedido=1000.00&tipoFrete=AEREO"
```

## Justificativas Técnicas dos Padrões de Design

### Padrão State
- **Por que foi escolhido?**: Essencial para modelar o ciclo de vida complexo de um pedido. Permite que o comportamento do objeto `Pedido` mude dinamicamente com base em seu estado interno, eliminando condicionais aninhadas e tornando o código mais limpo e fácil de entender.
- **Benefícios Técnicos**: Promove o Princípio Open/Closed (aberto para extensão, fechado para modificação), pois novos estados podem ser adicionados sem alterar as classes de estado existentes ou o contexto `PedidoStateManager`. Melhora a testabilidade ao isolar a lógica de cada estado.

### Padrão Strategy
- **Por que foi escolhido?**: Ideal para lidar com a variação nos algoritmos de cálculo de frete. Permite que diferentes lógicas de cálculo sejam encapsuladas em classes separadas e selecionadas em tempo de execução, sem impactar o código que utiliza o cálculo.
- **Benefícios Técnicos**: Aumenta a flexibilidade e a extensibilidade do sistema, facilitando a introdução de novas regras de frete. Reduz o acoplamento entre o `FreteCalculator` e as implementações específicas de frete, tornando o sistema mais modular e testável.

### Padrão Factory Method
- **Por que foi escolhido?**: Utilizado para centralizar a criação de instâncias de `FreteStrategy`. Garante que a lógica de instanciar a estratégia correta seja encapsulada, evitando que o código cliente precise conhecer os detalhes de implementação das estratégias concretas.
- **Benefícios Técnicos**: Promove o desacoplamento e a consistência na criação de objetos. Facilita a manutenção, pois qualquer alteração na forma como as estratégias são criadas é feita em um único local. Permite a validação dos tipos de frete suportados no ponto de criação.

## Conclusão e Próximos Passos

Este projeto serve como uma base sólida para um sistema de e-commerce, demonstrando a aplicação prática de padrões de design para resolver desafios comuns de engenharia de software. A arquitetura modular e extensível facilita a incorporação de novas funcionalidades, como:

- Integração com gateways de pagamento externos.
- Implementação de notificações (e-mail, SMS) em transições de estado.
- Adição de novos tipos de frete (e.g., frete expresso, frete internacional).
- Módulos de autenticação e autorização (já que foram explicitamente ignorados nesta versão).

O código é limpo, bem estruturado e acompanhado de documentação abrangente, tornando-o um excelente ponto de partida para futuras evoluções.

