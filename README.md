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

- Criar a classe [SpringDocOpenApiConfig](bikes/src/main/java/br/ifc/videira/bikes/config/SpringDocOpenApiConfig.java) no pacote `config`.

- Acessar a [documentação](http://localhost:8080/docs-bikes.html).

#### Acrescentando informações em Usuário

- Em [UsuarioController](bikes/src/main/java/br/ifc/videira/bikes/web/UsuarioController.java):
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
