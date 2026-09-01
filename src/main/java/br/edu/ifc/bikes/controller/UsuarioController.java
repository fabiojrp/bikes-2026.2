package br.edu.ifc.bikes.controller;

import br.edu.ifc.bikes.entity.Usuario;
import br.edu.ifc.bikes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario usuarioCriado  = usuarioService.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getById(id);

        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else {
            return ResponseEntity.notFound().build();
        }
    }



}