package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Cadastrar Comentário")
public record CadastroComentarioDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Size(max = 500, message = "Comentário deve ter no máximo 500 caracteres")
        String conteudo) {
}
