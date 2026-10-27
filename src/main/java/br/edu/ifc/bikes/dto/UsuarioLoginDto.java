package br.edu.ifc.bikes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioLoginDto(
        @NotBlank
        @Email(message = "Deve ser um email válido")
        String username,
        @NotBlank
        @Size(min = 6, max = 6)
        String password
){}
