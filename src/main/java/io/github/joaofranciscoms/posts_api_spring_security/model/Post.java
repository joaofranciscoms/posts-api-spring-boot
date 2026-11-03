package io.github.joaofranciscoms.posts_api_spring_security.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo", length = 300, nullable = false)
    private String titulo;

    @Column(name = "conteudo", columnDefinition = "TEXT")
    private String conteudo;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comentario> comentarios;
}
