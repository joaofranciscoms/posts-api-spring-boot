package io.github.joaofranciscoms.posts_api_spring_security.repository;

import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ComentarioRepository extends JpaRepository<Comentario, UUID>, JpaSpecificationExecutor<Comentario> {
}
