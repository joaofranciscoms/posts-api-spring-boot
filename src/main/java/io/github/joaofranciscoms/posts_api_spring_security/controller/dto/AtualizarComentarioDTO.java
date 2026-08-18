package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Atualizar Comentário")
public record AtualizarComentarioDTO(
        @NotBlank(message = "Campo obrigatório!")
        String conteudo) {
}
