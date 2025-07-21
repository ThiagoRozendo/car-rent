#  Sistema de Gerenciamento de Aluguel de Carros

Este é um projeto de um sistema desktop para gerenciamento completo de uma locadora de veículos, desenvolvido em Java com a interface gráfica construída em JavaFX. O sistema abrange desde o cadastro de entidades até a geração de relatórios e backups.

##  Funcionalidades Principais

O sistema conta com um conjunto de funcionalidades para a administração da locadora:

#### Gerenciamento de Entidades
- **Carros**: Cadastro, edição e remoção de veículos.
- **Clientes**: Cadastro, edição, remoção e visualização de histórico de aluguéis.
- **Funcionários**: Cadastro de Administradores e Atendentes com controle de acesso por tipo de cargo.

#### Fluxo de Aluguel
- **Catálogo Interativo**: Visualização de todos os carros em um layout de cards.
- **Busca e Filtro Avançados**:
    - Busca de carros por **Modelo**, **Marca** ou **Placa**.
    - Filtro para exibir ou ocultar carros que já estão alugados.
- **Carrinho de Aluguel**: Adição de até 2 carros para compor um novo aluguel.
- **Cadastro de Aluguel**: Tela dedicada para associar clientes, carros do carrinho e datas de início/fim.
- **Verificação de Atraso**: O sistema impede que um cliente com aluguéis atrasados realize um novo aluguel.
- **Finalização de Aluguéis**: Um funcionário pode marcar um aluguel como "Finalizado", tornando o carro disponível novamente.

#### Recursos
- **Sistema de Notificações**:
    - Clientes podem registrar interesse em carros já alugados.
    - O sistema gera uma notificação interna quando um carro reservado fica disponível.
    - Notificação gerada 3 dias antes da data de devolução de um aluguel.
- **Relatórios em PDF**: Funcionários podem gerar 5 tipos de relatórios detalhados:
    1.  Carros Disponíveis
    2.  Aluguéis Ativos
    3.  Histórico de Aluguéis de um Cliente específico
    4.  Clientes com Aluguéis Atrasados
    5.  Carros Agrupados por Categoria
- **Backup de Dados**: Geração de um backup completo dos dados do sistema (Carros, Clientes, Aluguéis e Funcionários) em formatos **CSV** ou **JSON**.

##  Como Usar

Para interagir com a aplicação, siga as instruções abaixo.

### Login Padrão de Administrador
Para acessar as funcionalidades administrativas (como cadastrar funcionários, gerar relatórios, etc.), utilize as seguintes credenciais:

-   **Usuário:** `admin@admin.com`
-   **Senha:** `12345678`

### Navegação Rápida (Tecla de Atalho)
Para uma experiência de uso mais fluida, a maioria das telas do sistema pode ser fechada para retornar à tela anterior.

-   Pressione a tecla **`ESC`** para voltar.

## ️ Tecnologias Utilizadas

-   **Linguagem**: Java 17+
-   **Interface Gráfica (GUI)**: JavaFX
-   **Geração de PDF**: Biblioteca iText 7
-   **Manipulação de JSON**: Biblioteca Gson (Google)
-   **Gerenciamento de Dependências**: Maven

##  Como Executar o Projeto

1.  **Pré-requisitos**:
    -   JDK 17 ou superior.
    -   Maven ou Gradle configurado.
    -   Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code).

2.  **Instalação**:
    -   Clone este repositório: `git clone https://github.com/ThiagoRozendo/car-rent.git`
    -   Abra o projeto na sua IDE.
    -   Aguarde a IDE baixar e sincronizar as dependências (iText e Gson) definidas no seu arquivo `pom.xml` ou `build.gradle`.

3.  **Execução**:
    -   Encontre a classe de inicialização `TelaInicial.java`.
    -   Execute-a para iniciar a aplicação.