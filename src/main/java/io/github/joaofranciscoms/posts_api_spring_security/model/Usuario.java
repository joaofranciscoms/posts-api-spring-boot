package io.github.joaofranciscoms.posts_api_spring_security.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", length = 150, nullable = false)
    private String login;

    @Column(name = "password", length = 300, nullable = false)
    private String password;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private Role role;
}
