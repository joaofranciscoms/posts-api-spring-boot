package io.github.joaofranciscoms.posts_api_spring_security.repository;

import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    UserDetails findByLogin(String login);
}
