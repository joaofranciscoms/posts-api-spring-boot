package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Cadastrar Post")
public record CadastroPostDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 5, max = 150, message = "Título deve ter entre 5 e 150 caracteres")
        String titulo,
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 20, max = 2000, message = "Conteúdo deve ter no mínimo 20 caracteres e no máximo 10000")
        String conteudo) {
}
