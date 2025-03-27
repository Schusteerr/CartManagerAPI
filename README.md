# 📊 Carrinho Inteligente: API Modular E-Commerce

Projeto API para gerenciar usuários, produtos e carrinho de compras usando **Spring Boot**, **MySQL** e **Docker**.

## 📖 Sumário
- [Objetivos do Projeto](#🎯-objetivos-do-projeto)
- [Tecnologias Usadas](#🚀-tecnologias-usadas)
- [Como Rodar o Projeto](#🔧-como-rodar-o-projeto)
- [Rotas Disponíveis](#📌-rotas-disponíveis)
- [Futuras Melhorias](#🔮-futuras-melhorias)

## 🎯 Objetivos do Projeto
1. **Gerenciamento de Usuários:** Permitir o cadastro, atualização, exclusão e consulta de informações dos usuários.
2. **Gerenciamento de Produtos:** Proporcionar o registro, manutenção e exclusão de produtos disponíveis para compra.
3. **Carrinho de Compras Dinâmico:** Oferecer funcionalidades para adicionar e remover produtos no carrinho de um usuário específico.
4. **Finalização de Compra Simplificada:** Permitir que os usuários finalizem suas compras, limpem o carrinho e alterem o status de compra.
5. **Postback de Notificações:** Gerar notificações automáticas via postback ao alterar o status do usuário para "comprou" ou "cancelou".

## 🚀 Tecnologias Usadas
- **Spring Boot 3.3.10**
- **MySQL 9.2.0**
- **Docker**

## 🔧 Como Rodar o Projeto

*Certifique-se de que terá as tecnologias listadas instaladas.*

### 1️⃣ Clone o Repositório

```bash
git clone https://github.com/Schusteerr/CartManagerAPI/
cd CartManagerAPI
```

### 2️⃣ Instale as Dependências

#### Caso use a linha de comando:

**Se você deseja rodar o projeto diretamente pela linha de comando**, será necessário instalar o Maven.

- **Baixe e instale o Maven**: [Link para o Maven](https://maven.apache.org/install.html)
- Após isso, execute o seguinte comando para instalar as dependências:

```bash
mvn clean install
```

### **Caso use uma IDE:**
Se você estiver utilizando uma IDE como IntelliJ IDEA, Eclipse ou VSCode, basta **importar o projeto como um projeto Maven** e a IDE irá cuidar das dependências automaticamente.

### 3️⃣ Configure o `docker-compose.yml` e `application.properties`

Siga o guia disponível em https://spring.io/guides/gs/accessing-data-mysql para configurar o seu banco de dados e variáveis de ambiente.

### 4️⃣ Suba o Banco de Dados com Docker

Certifique-se de que não haja outros contêineres utilizando as mesmas portas para evitar conflitos:

```bash
docker-compose up --build -d
```

### 5️⃣ Inicie a Aplicação

Inicie a API principal com o comando:

```bash
mvn spring-boot:run
```

### 6️⃣ Teste as Rotas

* Use os arquivos `.http` dentro do diretório `.Routes` para testar as rotas rapidamente no seu editor de código.
* Certifique-se de que o banco de dados esteja rodando corretamente via Docker.

## 📌 Rotas Disponíveis

### Usuário
- **POST /userpage** → Criar um usuário e gerar seu ID.
- **GET /userpage** → Listar todos os usuários.
- **GET /userpage/:id** → Listar um usuário específico pelo seu ID.
- **PUT /userpage/:id** → Atualizar um usuário específico pelo seu ID.
- **DELETE /userpage/:id** → Deletar um usuário específico pelo seu ID.

### Produtos
- **POST /products** → Criar um produto e gerar seu ID.
- **GET /products** → Obter lista de produtos.
- **PUT /products/:id** → Atualizar um produto específico pelo seu ID.
- **DELETE /products/:id** → Deletar um produto específico pelo seu ID.

### Carrinho de Compras
- **POST /cart/update** → Adicionar um produto no carrinho pelo e-mail do cliente e ID do produto.
- **POST /cart/remove** → Remover um produto do carrinho pelo e-mail do cliente e ID do produto.
- **POST /cart/finish** → Finalizar a compra, alterando o status para "comprado" ou "cancelado" e limpando o carrinho.

### 🔄 Notificação de Postback
Quando o status do usuário é alterado para **"comprou"** ou **"cancelou"**, um endpoint de postback é acionado, gerando uma notificação para sistemas externos. Isso garante que as mudanças de status sejam comunicadas de forma eficiente.

## 🔮 Futuras Melhorias
- Implementar proxy reverso com Nginx.
