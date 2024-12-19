# Produto API

Este projeto implementa uma **API REST** para gerenciar produtos, utilizando **Spring Boot**, **PostgreSQL**, **Flyway** e **Maven**.

## 📋 Pré-requisitos

Antes de começar, você precisa ter os seguintes softwares instalados em sua máquina:

- **Java** (versão 17 ou superior)
- **PostgreSQL** (versão 10 ou superior)
- **Maven** (versão 3.6 ou superior)
- **IntelliJ IDEA** (ou outra IDE de sua escolha)
- **cURL**, **Postman**, ou **PowerShell** (para testar a API)

---

## 🚀 Passos para o Projeto

### 1. Criando o Projeto no IntelliJ IDEA
1. Abra o IntelliJ IDEA.
2. Vá para **File > New > Project**.
3. Selecione **Maven** como tipo de projeto.
4. Preencha os campos:
    - **GroupId**: `com.exemplo`
    - **ArtifactId**: `produto-api`
    - **Version**: `1.0-SNAPSHOT`
5. Clique em **Finish** para concluir a criação do projeto.

---

### 2. Configurando o `pom.xml`
O arquivo `pom.xml` contém as dependências e configurações do Maven. Abaixo estão as dependências que você precisa adicionar:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.exemplo</groupId>
    <artifactId>produto-backend</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>Produto API</name>
    <description>API backend CRUD com Maven, PostgreSQL, Flyway e Spring Boot</description>
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <java.version>17</java.version>
        <spring-boot.version>3.1.6</spring-boot.version>
        <flyway.version>9.22.1</flyway.version>
        <postgresql.version>42.5.4</postgresql.version>
        <spring-boot-Start.version>3.4.0</spring-boot-Start.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>${postgresql.version}</version>
        </dependency>

        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>${flyway.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${spring-boot-Start.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <mainClass>com.exemplo.produto.ProdutoApiApplication</mainClass>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.flywaydb</groupId>
                <artifactId>flyway-maven-plugin</artifactId>
                <version>9.22.1</version>
                <configuration>
                    <url>jdbc:postgresql://localhost:5432/produto_db</url>
                    <user>postgres</user>
                    <password>867921kai</password>
                    <schemas>public</schemas>
                    <locations>
                        <location>classpath:db/migration</location>
                    </locations>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. Configurando o Banco de Dados
Este projeto utiliza o PostgreSQL. O Flyway gerencia a versão e execução dos scripts SQL automaticamente.

1. Crie o banco de dados `produto_db` no PostgreSQL:

```sql
CREATE DATABASE produto_db;
```

2. Atualize o arquivo `application.properties` em `src/main/resources` com as suas credenciais:

```text
spring.datasource.url=jdbc:postgresql://localhost:5432/produto_db
spring.datasource.username=postgres
spring.datasource.password=senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
```

### 4. Estrutura do Projeto

O projeto segue a arquitetura **MVC** (Model-View-Controller).

- O diretório `src/main/java` contém os pacotes:
    - `src/main/java/com/exemplo/produto/model` para as entidades.
    - `src/main/java/com/exemplo/produto/repository` para os repositórios.
    - `src/main/java/com/exemplo/produto/service` para os serviços.
    - `src/main/java/com/exemplo/produto/controller` para os controladores.
- O diretório `src/main/resources` contém os arquivos de configuração e scripts SQL.
- O diretório `src/test/java` contém os testes unitários.

#### 4.1 Criar a Entidade Produto

Arquivo: `src/main/java/com/exemplo/produto/model/Produto.java`

```java
package com.exemplo.produto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nome;
    private Double preco;

    // Getters e Setters
}
```

#### 4.2 Criar o Repositório

Arquivo: `src/main/java/com/exemplo/produto/repository/ProdutoRepository.java`

```java
package com.exemplo.produto.repository;

import com.exemplo.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
```

#### 4.3 Criar o Serviço

Arquivo: `src/main/java/com/exemplo/produto/service/ProdutoService.java`

```java
package com.exemplo.produto.service;

import com.exemplo.produto.model.Produto;
import com.exemplo.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

#### 4.4 Criar o Controlador

Arquivo: `src/main/java/com/exemplo/produto/controller/ProdutoController.java`

```java
package com.exemplo.produto.controller;

import com.exemplo.produto.model.Produto;
import com.exemplo.produto.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> getAll() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public Produto getById(@PathVariable Long id) {
        return produtoService.findById(id);
    }

    @PostMapping
    public Produto create(@RequestBody Produto produto) {
        return produtoService.save(produto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produtoService.delete(id);
    }
}
```

### 5. Criar Migração SQL

Arquivo: `src/main/resources/db/migration/V1__create_table_produto.sql`

```sql
CREATE TABLE produto (
    codigo BIGINT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DOUBLE PRECISION NOT NULL
);
```

### 6. Testando a API com cURL ou PowerShell

Após iniciar a aplicação, você pode testá-la com **cURL**, **Postman**, ou **PowerShell**.

#### 6.0 Iniciar a aplicação

```bash
mvn spring-boot:run
```

#### 6.1 Criar um produto com cURL ou PowerShell

```bash
Invoke-WebRequest -Uri "http://localhost:8080/api/produtos" -Method POST -Body '{"nome": "Produto A", "preco": 100.50}' -Headers @{ "Content-Type" = "application/json" }
```

#### 6.2 Listar produtos com cURL ou PowerShell

```bash
Invoke-WebRequest -Uri "http://localhost:8080/api/produtos" -Method GET
```

#### 6.3 Buscar por ID com cURL ou PowerShell

```bash
Invoke-WebRequest -Uri "http://localhost:8080/api/produtos/1" -Method GET
```

#### 6.4

Deletar produto com cURL ou PowerShell

```bash
Invoke-WebRequest -Uri "http://localhost:8080/api/produtos/1" -Method DELETE
```