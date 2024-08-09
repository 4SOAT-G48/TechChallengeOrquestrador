# TechChallengeOrquestrador

Este projeto focado em implementar a orquestração dos microserviços

Para documentações com visão geral de toda a solução acessar
a [wiki de documentação](https://github.com/4SOAT-G48/TechChallengeDocs/wiki)

---

## Pré requisitos para execução

Tanto para executar como para desenvolver são necessários os seguintes itens:

1. Instalar o docker;
1. Instalar o docker-compose;
1. Instalar o git.

---

## Executar aplicação via docker compose

1. Clonar o projeto git;
1. Crie o arquivo **.env** no mesmo diretório que o arquivo **docker-compose.yml** com o a estrutura do arquivo *
   *.env.example**
    - _Iremos passar as informacoes contidas no .env via email e/ou discord._
1. Via terminal entrar na pasta do projeto;
1. Executar o comando;
    ``` sh
    docker-compose up --build -d
    ```

---

## Desenvolvimento

### Pré requisitos adicionais

Além dos pré requisitos para execução para o desenvolvimento será necessário

1. Instalar o java versão 21, recomendamos a versão Eclipse Temurin;
    1. Link para download e instruções https://adoptium.net/installation/;
1. Maven versão 3.9.5 ou superior;
1. Instalar a IDE IntelliJ;
    1. https://www.jetbrains.com/idea/;
    1. Baixe a versão _*IntelliJ IDEA Community Edition*_;
1. dbeaver como cliente de conexão de banco.

### Para executar a aplicação direto na máquina

O projeto usa MongoDB para registro dos steps da orquestração e rabbit para fila de menssageria.

1. Crie o arquivo **.env** no mesmo diretório que o arquivo **docker-compose.yml** com o a estrutura do arquivo *
   *.env.example**
1. Via terminal entrar na pasta do projeto;
1. Executar o comando;
    ``` sh
    docker-compose up rabbitmq mongo mongo-express --build -d
    ```

---

## Testes

### Via terminal
