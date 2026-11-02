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

- Criar a classe [SpringDocOpenApiConfig](src/main/java/br/edu/ifc/bikes/config/SpringDocOpenApiConfig.java) no pacote `config`.

- Acessar a [documentação](http://localhost:8080/docs-bikes.html).

#### Acrescentando informações em Usuário

- Em [UsuarioController](src/main/java/br/edu/ifc//bikes/web/controller/UsuarioController.java):
   - No cabeçalho
   ```
   @Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
   ```

   - Em `create()`
   ```
   @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
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
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
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
  
## Atividade 01 - Alterar Senha

  - Modificar a **alteração de senha** para que, além do campo `nova senha`, seja informado também a `confirmação de senha` e a `senha atual`.
  - Será necessário criar a classe `UsuarioSenhaDTO.java` para requisição.
    - Não esquecer de validar os campos.
  - Fazer o tratamento de exceção criando a classe `PasswordInvalidException` para validar a senha atual e comparar a nova senha com a confirmação.


## Atividade 02 - Teste de Alterar Senha

  - Desenvolver métodos de teste para as 04 possibilidades: sucesso, senha atual inválida, senha e confirmação não conferem e campos inválidos (vazios, senhas com menos e mais que 6 caracteres).
      - Observar (no Controller) que alteração de senha não retorna um corpo de mensagem, ou seja, o teste não terá retorno.
          - Como não tem retorno, os testes de valores não são necessários.
      - O método para o status será o `isNoContent()`.

## Atividade 03 - Recurso Cliente
Um cliente só pode ser cadastrado se tiver um `usuário` associado. Ou seja, para se cadastrar,
o cliente deve ter um `usuário` cadastrado e autenticado.

**Recurso `Cliente`**:
- id (Long)
- nome (String)
- cpf (String) - deve ser único
- método `equals`

**Funcionalidades**:
- O cadastro só pode ser feito se o usuário estiver autenticado.
- O cliente pode consultar apenas os seus próprios dados.
- O Admin pode consultar os dados de todos os clientes.

**Classes a serem criadas:**
- Classes de DTO
- Controller
- Service
- Repository

**Requisitos**:
- O recurso `Cliente` deve ser protegido com autenticação.
- O recurso `Cliente` deve ser documentado com SpringDoc OpenAPI 3.1 e Swagger 3.
- Fazer validação dos campos de entrada.
- Campos de auditoria (os mesmos de `Usuário`)
