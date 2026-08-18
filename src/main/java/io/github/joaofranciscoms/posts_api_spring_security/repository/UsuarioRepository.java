package io.github.joaofranciscoms.posts_api_spring_security.repository;

import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByLogin(String login);
    Optional<Usuario> findByEmail(String email);
}
