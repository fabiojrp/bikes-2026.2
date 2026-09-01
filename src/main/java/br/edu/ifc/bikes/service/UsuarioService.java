package br.edu.ifc.bikes.service;

import br.edu.ifc.bikes.dto.UsuarioRequestDTO;
import br.edu.ifc.bikes.dto.UsuarioResponseDTO;
import br.edu.ifc.bikes.dto.mapper.UsuarioMapper;
import br.edu.ifc.bikes.entity.Usuario;
import br.edu.ifc.bikes.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponseDTO create(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioRequestDTO);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional()
    public UsuarioResponseDTO getById(Long id) {
        return usuarioMapper.toResponse(usuarioRepository.findById(id).orElse(null));
    }

    public UsuarioResponseDTO updatePassword(Long id, String password) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null){
            usuario.setPassword(password);
            return usuarioMapper.toResponse(usuarioRepository.save(usuario));
        }
        return null;
    }

    public List<UsuarioResponseDTO> getAll(){
        return usuarioMapper.toResponse(usuarioRepository.findAll());
    }
}