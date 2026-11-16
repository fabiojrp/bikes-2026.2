# Projeto Spring Boot - Locabike 💻

## Introdução
Apresentação do tema: Um sistema para aluguel temporário de bicicletas

### Modelagem do banco de dados
Fazer em conjunto com os estudantes

### Ferramentas/Tecnologias 🧑‍💻
- Linguagem: Java
- Framework: Spring Boot
- Gerenciador de projeto: Maven 
- BD: Mysql + Mysql Workbench
- IDE: IntelliJ IDEA

---

## Configuração inicial do projeto ✍️
1. Gerar projeto com o Spring Initializr
   - Spring 4 e Java 17
   - Dependências: Spring Web, Spring Data JPA, Spring Boot Devtools, Mysql Driver, H2 (bd para deste), Lombok (anotações para otimização)
   - Baixar .zip gerado, descompactar e abrir basta no IntelliJ
   - IntelliJ: configurar a versão do Java em **Project Structure** para a mesma versão do projeto Spring
  
2. Timezone e Locale 📅
   #### Timezone
  - Criar pacote `config`
  - Criar classe [SpringTimezoneConfig](source/v1/br/edu/ifc/bikes/config/SpringTimezoneConfig.java)

      #### Locale
   - Adicionar em application.properties:
   ```
      # LOCALE
      spring.web.locale-resolver=fixed
      spring.web.locale=pt_BR
   ```
3. Banco de dados
   #### Configuração do Mysql no application.properties
   Os parâmetros a mais na URL evitam problemas de segurança com o Mysql
   ```
      # Mysql Config
      spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
      spring.datasource.url=jdbc:mysql://localhost:3306/bikes?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
      spring.datasource.username=root
      spring.datasource.password=root
   ```

   #### Configuração do JPA no application.properties
   ```
      # JPA
      spring.jpa.show-sql=true //exibe o sql no log
      spring.jpa.properties.hibernate.format_sql=false //exibe em uma única linha
      spring.jpa.hibernate.ddl-auto=update //cria as tabelas a partir das classes das entidades definidas
   ```
<!--
--- 

## REST
   - Path params: usa quando o resultado é único - busca por id, chave primária
   - Query params: usa mais quando é para consulta geral, tipo filtro
-->
---

## Recursos de Usuários
   #### Criando as classes
   - Criar classe [Usuario](source/v1/br/edu/ifc/bikes/entity/Usuario.java) no pacote `entity`
   - Criar classe [UsuarioRepository](source/v1/br/edu/ifc/bikes/repository/UsuarioRepository.java) no pacote `repository` (pode utilizar o plugin JPA Buddy, criando um Spring Data JPA Repository)
   - Criar classe [UsuarioService](source/v1/br/edu/ifc/bikes/service/UsuarioService.java) no pacote `service`
   - Criar classe [UsuarioController](source/v1/br/edu/ifc/bikes/web/controller/UsuarioController.java) no pacote `web.controller`

   
   #### Cadastrar usuário
   - Criar método `create()` em [UsuarioService](source/v1/br/edu/ifc/bikes/service/UsuarioService.java)
   - Criar método `create()` em [UsuarioController](source/v1/br/edu/ifc/bikes/web/controller/UsuarioController.java)


      ##### Teste no Postman
      - Criar a coleção `Bikes`
      - Criar um request `POST` http://localhost:8080/api/v1/usuarios
      - Preencher *body* no campo *raw* o JSON:
      ```
         {
            "username": "fabiojrp@gmail.com",
            "password": "123456",
            "role": "ROLE_ADMIN"
         }
      ```
      - Os campos de auditoria serão inseridos posteriormente
      - A senha será criptografada posteriormente fazendo uso de biblioteca

   #### Buscar usuário por id
   - Criar método `getById()` em [UsuarioService](source/v1/br/edu/ifc/bikes/service/UsuarioService.java) e [UsuarioController](source/v1/br/edu/ifc/bikes/web/controller/UsuarioController.java)

      ##### Teste no Postman
      - Na coleção `Bikes` criar um request `GET` http://localhost:8080/api/v1/usuarios/1

   #### Alterar senha
   - Criar método `update()` em [UsuarioService](source/v1/br/edu/ifc/bikes/service/UsuarioService.java) e [UsuarioController](source/v1/br/edu/ifc/bikes/web/controller/UsuarioController.java)

      ##### Teste no Postman
      - Na coleção `Bikes` criar um request `GET` http://localhost:8080/api/v1/usuarios/1

   #### Buscar todos os usuários
   - Criar método `getAll()` em [UsuarioService](source/v1/br/edu/ifc/bikes/service/UsuarioService.java) e [UsuarioController](source/v1/br/edu/ifc/bikes/web/controller/UsuarioController.java)

      ##### Teste no Postman
      - Na coleção `Bikes` criar um novo request `GET` http://localhost:8080/api/v1/usuarios

---

### DTO - *Data Transfer Object*
Em aplicações Java modernas (Spring Boot 3.x / Java 21 e 25), o DTO deixou de ser apenas uma forma de "transportar dados" entre camadas e passou a ser uma ferramenta importante para desacoplamento, segurança e versionamento de APIs.

Um DTO é um objeto cuja única responsabilidade é transportar dados entre partes da aplicação, normalmente entre:
- Cliente ↔ API REST
- Controller ↔ Service
- Microsserviços
- Aplicações diferentes

Ele não contém regras de negócio (ou contém o mínimo possível).

#### Exemplo: entidade `Cliente.java`
```
@Entity
public class Cliente {
    @Id
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
}
```
O campo `senha` não deveria circular pela aplicação; e `dataNascimento`, na maioria das vezes, não é necessário.

O DTO se propõe a resolver esse problema. E, desde o Java 16 (melhorado a partir da 21), a solução tornou-se ainda mais simples, com a criação do ***record***.

#### DTO `ClienteDto.java`
```
public record ClienteDto(
    Long id,
    String nome,
    String email
){}
```

É comum separar os DTOs, em um DTO de requisição (*create*), sem o campo `id`, por exemplo; e um DTO de resposta (*get*), agora com o campo `id`.

### Alteração da aplicação para uso do DTO
#### Criando o DTO para criar um novo usuário

   - Criar as classes [UsuarioRequestDto.java](source/v2/br/edu/ifc/bikes/dto/UsuarioRequestDto.java) e [UsuarioResponseDto.java](source/v2/br/edu/ifc/bikes/dto/UsuarioResponseDto.java) no pacote `dto` na forma de ***record***.
   - Criar a classe [UsuarioMapper.java](source/v2/br/edu/ifc/bikes/dto/mapper/UsuarioMapper.java) no pacote `dto.mapper`.

O arquivo `pom.xml` precisa ser modificado para dar suporte ao MapStruct utilizado no UsuarioMapper.
   - Nas dependências:
   ```
      <dependency>
         <groupId>org.mapstruct</groupId>
         <artifactId>mapstruct</artifactId>
         <version>1.6.3</version>
         <scope>compile</scope>
      </dependency>
   ```
   - Em execução>configuração>anotação:
   ```
      <path>
         <groupId>org.mapstruct</groupId>
         <artifactId>mapstruct-processor</artifactId>
         <version>1.6.3</version>
      </path>
   ```

As classes [UsuarioService](source/v2/br/edu/ifc/bikes/service/UsuarioService.java) e [UsuarioController](source/v2/br/edu/ifc/bikes/web/controller/UsuarioController.java) devem ser modificadas para contemplar os DTOs de requisição e resposta.

#### Buscar usuário por ID
Modificar as classes [UsuarioService](source/v2/br/edu/ifc/bikes/service/UsuarioService.java) e [UsuarioController](source/v2/br/edu/ifc/bikes/web/controller/UsuarioController.java) para receber `UsuarioRequestDto` e retornar um `UsuarioResponseDto`, e não mais um `Usuario`.


#### Alterar senha
Modificar a funcionalidade para receber: novaSenha, confirmaSenha, senhaAtual. Para isto será necessário criar um DTO específico para receber esta requisição.

   - Criar a classe [UsuarioSenhaDto.java](source/v2/br/edu/ifc/bikes/dto/UsuarioSenhaDto.java) no pacote `dto`.
   - Modificar o método `updatePassword` na classe  [UsuarioService](source/v2/br/edu/ifc/bikes/service/UsuarioService.java)  para fazer as devidas verificações/validações.
   - Modificar o método `updatePassword` na classe [UsuarioController](source/v2/br/edu/ifc/bikes/web/controller/UsuarioController.java) para receber o novo DTO.
   - Modificar o método `updatePassword` para retornar *void*, já que para esse tipo de requisição necessita apenas informar que deu certo (através de um **status HTTP**).
   - Modificar a requisição no Postman.

#### Buscar todos os usuários
   - Criar um novo método na classe [UsuarioMapper](source/v2/br/edu/ifc/bikes/dto/mapper/UsuarioMapper.java): `List<UsuarioResponseDto> toResponse(List<Usuario> usuarios)`.
   - Modificar o método `getAll()` nas classes [UsuarioService](source/v2/br/edu/ifc/bikes/service/UsuarioService.java) e [UsuarioController](source/v2/br/edu/ifc/bikes/web/controller/UsuarioController.java), adaptando-os para retorno de `List<UsuarioResponseDto>`.


---

### *Bean Validation*
A validação no back-end em APIs REST é importante por várias razões: segurança, consistência, melhoria da experiência do usuário, manutenção simplificada (centraliza a lógica).

Será utilizada a biblioteca [Jakarta Bean Validation](https://beanvalidation.org/). A configuração deve ser feita no `pom.xml`.
   ```
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
   ```
#### Validação [UsuarioRequestDTO](bikes/src/main/java/br/edu/ifc/bikes/dto/UsuarioRequestDTO.java)
`@NotBlank`: verifica se não é `null`, se não está vazia e se tem ao menos 1 caracter - utilizar em ambos atributos.
`@Email`: verifica formato padrão de email (não verifica se vier `null`); a vericação padrão da biblioteca é bem simples. É possível criar uma expressão regular com uma melhor validação - atributo `username`.
`@Size`: especifica tamanho mínimo e máximo da String (útil para senha) - atributo `password`.

- Acrescentar a anotação `@Valid` antes de `@RequestBody` no método `create()` na classe `UsuarioController`.

#### Validação [UsuarioSenhaDTO](bikes/src/main/java/br/edu/ifc/bikes/dto/UsuarioSenhaDto.java)
`@NotBlank` e `@Size` para todos os atributos.

- Acrescentar a anotação `@Valid` antes de `@RequestBody` no método `updatePassword()` na classe `UsuarioController`.

---

### Tratamento de Exceções com RestControllerAdvice

- Criar pacote `exception` em `web`.
- Criar classe [ErrorMessage](bikes/src/main/java/br/edu/ifc/bikes/web/exception/ErrorMessage.java).

Tratamento de **campos inválidos**:
- Criar classe [ApiExceptionHandler](bikes/src/main/java/br/edu/ifc/bikes/web/exception/ApiExceptionHandler.java).

Tratamento de **usuário único**:
- Alterar o método `salvar` em [UsuarioService](bikes/src/main/java/br/edu/ifc/bikes/service/UsuarioService.java).
- Criar a classe [UsernameUniqueViolationException](bikes/src/main/java/br/edu/ifc/bikes/exception/UsernameUniqueViolationException.java).
- Alterar a classe [ApiExceptionHandler](bikes/src/main/java/br/edu/ifc/bikes/web/exception/ApiExceptionHandler.java).

Tratamento de **username id não encontrado**:
- Alterar o método `getById` em [UsuarioService](bikes/src/main/java/br/edu/ifc/bikes/service/UsuarioService.java).
- Criar a classe [UsernameUniqueViolationException](bikes/src/main/java/br/edu/ifc/bikes/exception/EntityIdNotFoundException.java).
- Alterar a classe [ApiExceptionHandler](bikes/src/main/java/br/edu/ifc/bikes/web/exception/ApiExceptionHandler.java).

---

### Atividade - Alterar senha

- Modificar a **alteração de senha** para que, além do campo `nova senha`, seja informado também a `confirmação de senha` e a `senha atual`.
- Criar a classe [UsuarioSenhaDto.java](source/v2/br/edu/ifc/bikes/dto/UsuarioSenhaDto.java) como DTO para a requisição.
- Criar um tratamento de exceção próprio com o nome `PasswordInvalidException` para validar a senha atual e comparar a nova senha com a confirmação.

---

### Documentação com SpringDoc OpenAPI 3.1 e Swagger 3

- [Site oficial](https://springdoc.org/) - [Configuração](https://springdoc.org/#getting-started)

Dependência (`pom.xml`):
```
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>3.1.1</version>
   </dependency>
```

`Application.properties`
```
   springdoc.swagger-ui.path=/docs-bikes.html
   # Código gerado para acessar a documentação da API no Swagger UI: http://localhost:8080/docs-bikes.html
   springdoc.api-docs.path=/docs-bikes
   springdoc.packages-to-scan=br.edu.ifc.bikes.web.controller
```

- Criar a classe [SpringDocOpenApiConfig](bikes/src/main/java/br/edu/ifc/bikes/config/SpringDocOpenApiConfig.java) no pacote `config`.

- Acessar a [documentação](http://localhost:8080/docs-bikes.html).

#### Acrescentando informações em Usuário

- Em [UsuarioController](bikes/src/main/java/br/edu/ifc/bikes/web/controller/UsuarioController.java):
   - No cabeçalho
   ```
   @Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
   ```

   - Em `create()`
   ```
   @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
   ```

   - Em `getById()`
   ```
   @Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
   ```

   - Em `updatePassword()`
   ```
   @Operation(summary = "Atualizar senha", description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
   ```

   - Em `getAll()`
   ```
   @Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                                    content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))))
            })
   ```
   ---
   ## Teste de Software
   ### Configuração
   Dependências (`pom.xml`):
   - H2 Database
   - Spring Boot Starter Test
   ```
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
   </dependency>
   ```
   - Webflux (biblioteca de teste)
   ```
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
      <version>4.2.0-M1</version>
      <scope>compile</scope>
   </dependency>
   ```

   ### Desenvolvimento
   O desenvolvimento dos testes dar-se-á na pasta `test` (a classe `BikesApplicatonTestes.java` pode ser removida).
   - Criar uma pasta `resources` e dentro um arquivo [application.properties](bikes/src/test/resources/application.properties), equivalente à pasta `main`.
      - Neste arquivo será configurado um banco de dados (H2 (mas poderia ser qualquer um outro)) como base para os testes.
   - Ainda em `resources` criar as pastas `sql/usuarios`, e dentro criar o arquivo [usuarios-insert.sql](bikes/src/test/resources/sql/usuarios/usuarios-insert.sql), com alguns dados iniciais para testes; e o arquivo [usuarios-delete.sql](bikes/src/test/resources/sql/usuarios/usuarios-delete.sql], para limpar a base após os testes.
   - Criar a classe [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java), no pacote `br.edu.ifc.bike`: a terminação **IT** está relacionada com integração, mesmo estando relacionado com um teste ponto a ponto.

   #### Teste de criação de usuário
   - Criar o método `criarUsuario_UsernamePasswordsValido_RetornaUsuarioCriado()`em [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java).
      - o nome do método segue o padrão [Comportamento]\_[Condição]_[Resultado]

   #### Teste de username já existente (erro 409)
   - Criar o método `criarUsuario_UsernameRepetido_RetornaErrorMessageStatus409()`em [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java).

   #### Teste de username inválido (erro 422)
   - Criar o método `criarUsuario_UsernameInvalido_RetornaErrorMessageStatus422()`em [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java).

   #### Teste de password inválido (erro 422)
   - Criar o método `criarUsuario_PasswordInvalido_RetornaErrorMessageStatus422()`em [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java).

   #### Teste de busca de usuário
   - Criar o método `buscarUsuario_IdExistente_RetornaUsuario()`em [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java).

   #### Teste de busca de usuário inexistente
   - Criar o método `buscarUsuario_IdInexistente_RetornaErrorMessageStatus404()`em [UsuarioIT](bikes/src/test/java/br/edu/ifc/bikes/UsuarioIT.java).

--- 

   ## Spring Security e JTW

   ### Configuração
   Dependências (`pom.xml`):
   ```
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt-api</artifactId>
      <version>0.13.0</version>
      <scope>compile</scope>
   </dependency>
   <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt-impl</artifactId>
      <version>0.13.0</version>
      <scope>runtime</scope>
   </dependency>
   <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt-jackson</artifactId>
      <version>0.13.0</version>
      <scope>runtime</scope>
   </dependency>
   ```

   ### Desenvolvimento
   - Criar classe [SpringSecurityConfig](bikes/src/main/java/br/edu/ifc/bikes/config/SpringSecurityConfig.java).
   - Criar pacote `jwt` em `br.edu.ifc.bikes`.
   - Criar classe [JwtToken](bikes/src/main/java/br/edu/ifc/bikes/jwt/JwtToken.java)
   - Criar classe [JwtUtils](bikes/src/main/java/br/edu/ifc/bikes/jwt/JwtUtils.java)
   - Criar classe [JwtUserDetails](bikes/src/main/java/br/edu/ifc/bikes/jwt/JwtUserDetails.java)
   - Criar classe [JwtUserDetailsService](bikes/src/main/java/br/edu/ifc/bikes/jwt/JwtUserDetailsService.java)
      - Criar métodos de busca por `username` e por `role` em [UsuarioService](bikes/src/main/java/br/edu/ifc/bikes/service/UsuarioService.java) e em [UsuarioRepository](bikes/src/main/java/br/edu/ifc/bikes/repository/UsuarioRepository.java)
   - Criar classe [AutenticacaoController](bikes/src/main/java/br/edu/ifc/bikes/web/controller/AutenticacaoController.java)
   - Criar classe [UsuarioLoginDto](bikes/src/main/java/br/edu/ifc/bikes/dto/UsuarioLoginDto.java)
   - Criar classe [JwtAuthorizationFilter](bikes/src/main/java/br/edu/ifc/bikes/jwt/JwtAuthorizationFilter.java)
      - Adicionar o método abaixo em `UsuarioService`:
      ```
      @Bean
      public JwtAuthorizationFilter jwtAuthorizationFilter(JwtUserDetailsService detailsService) {
         return new JwtAuthorizationFilter(detailsService);
      }
      ```
      - E adicionar a instrução abaixo no método `filterChain(HttpSecurity http, JwtAuthorizationFilter jwtAuthorizationFilter)` da classe `SpringSecurityConfig`:
      ```
      .requestMatchers(HttpMethod.POST,"/api/v1/auth").permitAll()
      ```
      e
      ```
      .addFilterBefore(                     
         jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
      ```
   - Em [UsuarioService](bikes/src/main/java/br/edu/ifc/bikes/service/UsuarioService.java) acrescentar:
      ```      
      private final PasswordEncoder passwordEncoder;
      ```
      - Em `create()` acrescentar:
      ```
      usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
      ```
      - Em `updatePassword()` trocar:

      ```
      if (!usuario.getPassword().equals(senhaAtual)) {
      
      por

      if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
      ```
      e
      ```
      usuario.setPassword(novaSenha);

      por

      usuario.setPassword(passwordEncoder.encode(novaSenha));

      ```

      #### Testando requisições seguras
      - Primeiro testar as requisições sem autenticação (403 - forbiden).
      - Apagar usuários atuais e cadastrar um novo, agora com senha cadastrada.
      - Fazer login (auth) e copiar token.
      - Colar token nas requisições seguras em: authorization -> Bearer token

### Erros da última aula
#### falta do FINAL


---
## Habilitando o sistema de auditoria
- Criar classe [SpringJpaAuditingConfig](bikes/src/main/java/br/edu/ifc/bikes/config/SpringJpaAuditingConfig.java) em  `config`.
   - Alterar a classe [Usuario](bikes/src/main/java/br/edu/ifc/bikes/entity/Usuario.java) para permitir a auditoria.
      - Anotação na classe:
      ```
      @EntityListeners(AuditingEntityListener.class)
      ```
      - Anotações nos atributos:
      ```
      @CreatedDate
      private LocalDateTime dataCriacao;
      @LastModifiedDate
      private LocalDateTime dataModificacao;
      @CreatedBy
      private String criadoPor;
      @LastModifiedBy
      private String modificadoPor;
      ```

---
## Permissões de acesso
- Alterar o role do admin no BD para ADMIN
### Recuperar apenas os próprios dados (exceto o Admin)
- Em `UsuarioController`, no método `getBy()`, acrescentar:
```
@PreAuthorize("hasRole('ADMIN')")
```
- Testar com login de ADMIN e depois testar com login de CLIENTE
- Alterar anotação para:
```
@PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)")
```
- Testar noavamente.

### Alteração de senha - apenas a própria senha
- Em `UsuarioController`, no método `updatePassword()`, acrescentar:
```
@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND #id == authentication.principal.id")

```

### Buscar todos os usuários - apenas o Admin
```
@PreAuthorize("hasRole('ADMIN')")
```
---

## Relatórios com Jaspersoft Studio
#### Ferramenta
- Fazer download da versão community (gratuita) do [site ofifical](https://community.jaspersoft.com/).

#### Criando o relatório
- Na aba *Project Explorer* criar:
   - uma pasta `images` onde ficarão as imagens do modelo do relatório (ex.: cabeçalho).
   - um novo *Jasper Report* A4 em branco.

#### Configurando a base de dados
- Na aba *Repository Explorer*, criar um novo *Data Adapter*.
   - Tipo: *Database JDBC Connection*.
   - Configurar a fonte de dado.
   - E na aba abaixo, configurar o classpath para o driver do banco escolhido.

#### Definindo consulta que irá popular o relatório
- Clicar no ícone *Dataset and query editor*, acima do relatório.
- Escolher o *data adapter* criado.
- Na área de texto do lado direito, criar o sql de consulta.
- Clicar em *read fields*.
- Por fim, na aba *data preview* testar a consulta em *Refresh preview data*

#### Montando o relatório
- Adicionar a imagem ao cabeçalho
   - Criar um **parâmetro** (janela de propriedades à esquerda) com o valor *IMG_DIR*, e desmarcar a opção *is for prompt*.
   - Nas proprieadades da imagem, na aba *image*, em *expression*, colocar:
      - $P{IMG_DIR} + "images/bikes2.png"
- Adiconar os campos na formulário.
- Após finalizar e testar, compilar o formulário.

#### Configurando o relatório na aplicação (IntelliJ)
- Adicionar no `pom.xml`:
```
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports</artifactId>
    <version>7.0.8</version>
    <scope>compile</scope>
</dependency>

<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports-pdf</artifactId>
    <version>7.0.8</version>
</dependency>

```
- Em `resources`, criar um nova pasta `reports`, e colar nela o arquivo do relatório compilado (`.jasper`).
   - Nessa mesma pasta, colar a pasta `image` com a imagem do topo do relatório.
- Criar a classe [JasperService](bikes/src/main/java/br/edu/ifc/bikes/service/JasperService.java) no pacote `service`.

- Em [ClienteController](bikes/src/main/java/br/edu/ifc/bikes/web/controller/ClienteController.java), acrescentar o endpoint:
```
 @GetMapping("/relatorio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> gerarRelatorio(HttpServletResponse response) {
        byte[] relatorioBytes = jasperService.generatePdf();
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename="+System.currentTimeMillis()+".pdf");
        try {
            response.getOutputStream().write(relatorioBytes);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }
        return ResponseEntity.ok().build();
    }
```
