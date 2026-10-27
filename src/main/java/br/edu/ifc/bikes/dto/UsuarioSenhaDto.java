package br.edu.ifc.bikes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioSenhaDto(
        @NotBlank(message = "A nova senha é obrigatória")
        @Size(min=6, max=6)
        String novaSenha,
        @NotBlank(message = "A confirmação senha é obrigatória")
        @Size(min=6, max=6)
        String confirmaSenha,
        @NotBlank(message = "A senha atual é obrigatória")
        @Size(min=6, max=6)
        String senhaAtual
) {
}
