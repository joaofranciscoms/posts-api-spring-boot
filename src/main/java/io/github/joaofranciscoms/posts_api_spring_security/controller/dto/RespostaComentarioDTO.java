package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "Resposta Comentario")
public record RespostaComentarioDTO(
        UUID id,
        String conteudo,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataCriacao,
        String nomeAutor,
        UUID idAutor,
        UUID idPost
) {

}
