package br.edu.ifc.bikes.service;

import br.edu.ifc.bikes.dto.UsuarioRequestDTO;
import br.edu.ifc.bikes.dto.UsuarioResponseDTO;
import br.edu.ifc.bikes.dto.mapper.UsuarioMapper;
import br.edu.ifc.bikes.entity.Usuario;
import br.edu.ifc.bikes.exception.EntityNotFoundException;
import br.edu.ifc.bikes.exception.PasswordInvalidException;
import br.edu.ifc.bikes.exception.UsernameUniqueViolationException;
import br.edu.ifc.bikes.jwt.JwtAuthorizationFilter;
import br.edu.ifc.bikes.jwt.JwtUserDetailsService;
import br.edu.ifc.bikes.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO create(UsuarioRequestDTO usuarioRequestDTO) {
        try {
            Usuario usuario = usuarioMapper.toUsuario(usuarioRequestDTO);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioMapper.toResponse(usuarioRepository.save(usuario));
        }catch (RuntimeException ex){

            throw new UsernameUniqueViolationException(String.format("O nome do usuário '%s' já existe", usuarioRequestDTO.username()));
        }
    }

    @Transactional()
    public UsuarioResponseDTO getById(Long id) {
        return usuarioMapper.toResponse(usuarioRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("Usuário com id=%d não encontrado", id))
        ));
    }

    public UsuarioResponseDTO updatePassword(Long id, String novaSenha, String confirmaSenha, String senhaAtual) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("As senhas não coincidem");
        }
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(String.format("Usuário %s não encontrado", id))
        );
        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())){
            throw new PasswordInvalidException("Senha atual incorreta");
        }
        usuario.setPassword(passwordEncoder.encode(novaSenha));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponseDTO> getAll(){
        return usuarioMapper.toResponse(usuarioRepository.findAll());
    }

    @Transactional()
    public Usuario buscarPorNome(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException(String.format("Usuário '%s' não encontrado", username)));
    }

    public Usuario.Role buscarRolePorUsername(String username){
        return usuarioRepository.findRoleByUsername(username);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtUserDetailsService detailsService){
        return new JwtAuthorizationFilter(detailsService);
    }
}