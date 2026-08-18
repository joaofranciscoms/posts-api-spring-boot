package io.github.joaofranciscoms.posts_api_spring_security.repository;

import io.github.joaofranciscoms.posts_api_spring_security.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}
