# Sistema de Vigilância

**Nomes:** Vitor Emmanuel de Mello Siqueira Bitencourt, Gustavo de Moura Daniel, Dioniso Ilha Antunes e Vitor Dahmer Santos  
**Turma:** 4º Semestre - ADS  

---

## Para rodar:

**Back-End:** Pelo terminal, acesse a pasta AlarmesDoADS/backend, isntale as dependencias do mvn e execute o maven:

```cmd

    cd backend
    mvn clean install
    mvn spring-boot:run

```

**Front-End:** Pelo terminal, acesse a pasta AlarmesDoADS/front, instale as dependências e execute o react:

```cmd

    cd frontend
    npm i
    npm run dev

```

 **Documentação completa:** Disponível em: ./backend/target/apidocs/index.html

---

Nosso projeto busca desenvolver um sistema com interface web e uso de banco de dados, voltado para empresas de vigilância e segurança. O sistema contará com as funcionalidades essenciais, como:

- Cadastro de clientes e locais monitorados
- Painel de monitoramento em tempo real
- Registro de ocorrências
- Controle de rondas com check-in e check-out
- Escala de vigilantes
- Portal do cliente

## Perfis de Acesso

O sistema será dividido em 3 perfis de acesso:

1. **Administrador**: Controle total da plataforma.
2. **Vigilante**: Acesso ao seu roteiro de rondas e relatórios de campo.
3. **Cliente**: Acesso restrito ao seu contrato e relatórios do andamento da vigilância.

## Telas do Sistema

### 1. Tela de Login

A tela de login será simples, com autenticação por e-mail e senha. Dependendo do tipo de login, o sistema redireciona o usuário para o painel adequado, com permissões e funcionalidades personalizadas.

### 2. Cadastro de Cliente ou Vigilante

Essa tela permite o registro tanto dos vigilantes quanto dos clientes. Ela contém os dados básicos para o cadastro de cada tipo de usuário e também uma opção para informar o tipo de cargo que o usuário deseja ocupar. A criação de contas de vigilantes deve ser aprovada pelos administradores.

### 3. Dashboard Geral (Administrador)

A central de comando do sistema, onde o administrador pode visualizar indicadores em tempo real, como:

- Número de rondas realizadas
- Ocorrências registradas
- Alarmes disparados
- Casos de desaparecimento ativos
- Novos relatos recebidos

O painel também conta com um sistema de logs para registrar a atividade do sistema e atalhos para navegar para outros módulos.

### 4. Tela de Escala e Rondas (Vigilante)

Essa tela lista todas as rondas programadas por data e horário, com detalhes sobre o local de atuação. O vigilante pode:

- Iniciar uma ronda
- Registrar presença com check-in
- Finalizar com check-out
- Adicionar observações ou registrar ocorrências

Um mapa com rotas planejadas pode ser incluído para orientar o trajeto.

### 5. Tela de Registro de Ocorrências (Vigilante)

Aqui, os vigilantes podem registrar ocorrências de segurança, como tentativas de invasão, alarmes disparados e comportamentos suspeitos. O formulário de ocorrência inclui:

- Tipo de ocorrência
- Data
- Local
- Descrição
- Possibilidade de anexar fotos ou vídeos

Cada ocorrência pode ser marcada com um status (em análise, resolvida, encaminhada), gerando relatórios posteriores.

### 6. Painel de Monitoramento (Vigilante)

O painel de monitoramento exibe em tempo real os locais monitorados, com status de alarme (ativo, disparado, desativado). Pode incluir:

- Miniaturas de câmeras ao vivo
- Informações sobre vigilantes em campo
- Atalhos para comunicação com os vigilantes no local
- Mapa interativo mostrando pontos críticos da cidade ou região monitorada

### 7. Painel do Cliente

No portal do cliente, ele poderá visualizar:

- Relatórios de rondas e ocorrências no seu endereço
- Visualizar pagamentos
- Verificar o status do alarme
- Solicitar suporte diretamente
- Histórico de atividades dos vigilantes
