package io.github.joaofranciscoms.posts_api_spring_security.validator;

import io.github.joaofranciscoms.posts_api_spring_security.exceptions.EmailDuplicadoException;
import io.github.joaofranciscoms.posts_api_spring_security.exceptions.UsernameDuplicadoException;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository repository;

    public void verificarUsuarioDuplicado(Usuario usuario){
        verificarUsernameDuplicado(usuario);
        verificarEmailDuplicado(usuario);
    }

    private void verificarUsernameDuplicado(Usuario usuario){
        if(isUsernameDuplicado(usuario)){
            throw new UsernameDuplicadoException("Este username já está em uso!");
        }
    }

    private void verificarEmailDuplicado(Usuario usuario){
        if(isEmailDuplicado(usuario)){
            throw new EmailDuplicadoException("Este email já está em uso!");
        }
    }

    private boolean isUsernameDuplicado(Usuario usuario){
        Optional<Usuario> usuarioOptional = repository.findByLogin(usuario.getLogin());

        if(usuarioOptional.isEmpty()){
            return false;
        }

        if(usuario.getId() == null){
            return true;
        }

        return !usuario.getId().equals(usuarioOptional.get().getId());
    }

    private boolean isEmailDuplicado(Usuario usuario){
        Optional<Usuario> usuarioOptional = repository.findByEmail(usuario.getEmail());

        if(usuarioOptional.isEmpty()){
            return false;
        }

        if(usuario.getId() == null){
            return true;
        }

        return !usuario.getId().equals(usuarioOptional.get().getId());
    }
}
