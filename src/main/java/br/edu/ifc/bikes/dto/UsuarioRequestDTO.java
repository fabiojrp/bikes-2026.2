package br.edu.ifc.bikes.dto;

public record UsuarioRequestDTO(
    String username,
    String password
) { }