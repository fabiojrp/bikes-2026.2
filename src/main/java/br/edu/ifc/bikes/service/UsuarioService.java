package br.edu.ifc.bikes.service;

import br.edu.ifc.bikes.entity.Usuario;
import br.edu.ifc.bikes.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario getById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }









}
