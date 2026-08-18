package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(name = "Resposta Post")
public record RespostaPostDTO(
        UUID id,
        String titulo,
        String conteudo,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataCriacao,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataAtualizacao,
        String autor,
        List<RespostaPostComentarioDTO> comentarios) {
}
