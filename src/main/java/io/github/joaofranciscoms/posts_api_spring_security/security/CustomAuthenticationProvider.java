package io.github.joaofranciscoms.posts_api_spring_security.security;

import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = service.getByLogin(login);

        if(usuarioEncontrado == null){
            throw getErroUsuarioNaoEncontrado();
        }

        String senhaCriptografada = usuarioEncontrado.getPassword();

        boolean senhamBatem = encoder.matches(senhaDigitada, senhaCriptografada);

        if(senhamBatem){
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw getErroUsuarioNaoEncontrado();
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
