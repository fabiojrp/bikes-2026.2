package br.edu.ifc.bikes.web.controller;

import br.edu.ifc.bikes.dto.UsuarioRequestDTO;
import br.edu.ifc.bikes.dto.UsuarioResponseDTO;
import br.edu.ifc.bikes.entity.Usuario;
import br.edu.ifc.bikes.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioCriado  = usuarioService.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.getById(id);

        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //o PATCH deve ser utilizado, no lugar no PUT, quando a atualização for de poucos campos.
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updatePassword(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuario){
        UsuarioResponseDTO updateUsuario = usuarioService.updatePassword(id, usuario.password());
        if (updateUsuario != null){
            return ResponseEntity.ok(updateUsuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
        List<UsuarioResponseDTO> usuarios = usuarioService.getAll();
        return ResponseEntity.ok(usuarios);
    }
}