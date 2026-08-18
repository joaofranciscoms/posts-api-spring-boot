package io.github.joaofranciscoms.posts_api_spring_security.service;

import io.github.joaofranciscoms.posts_api_spring_security.exceptions.UsuarioNaoEncontradoException;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.UsuarioRepository;
import io.github.joaofranciscoms.posts_api_spring_security.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final UsuarioValidator validator;

    public void save(Usuario usuario){
        validator.verificarUsuarioDuplicado(usuario);
        String senha = usuario.getPassword();
        usuario.setPassword(encoder.encode(senha));
        repository.save(usuario);
    }

    public Optional<Usuario> findById(UUID id){
        return repository.findById(id);
    }

    public Usuario getByLogin(String username){
        return repository.findByLogin(username).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encotrando!"));
    }

    public Usuario getByEmail(String email){
        return repository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encotrando!"));
    }
}
