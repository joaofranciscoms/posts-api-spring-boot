package io.github.joaofranciscoms.posts_api_spring_security.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comentario")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comentario {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "conteudo", columnDefinition = "TEXT")
    private String conteudo;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Post post;
}
