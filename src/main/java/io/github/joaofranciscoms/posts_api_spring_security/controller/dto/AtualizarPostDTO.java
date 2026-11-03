package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import jakarta.validation.constraints.Size;

public record AtualizarPostDTO(
        @Size(min = 5, max = 150, message = "Título deve ter entre 5 e 150 caracteres")
        String titulo,
        @Size(min = 20, max = 2000, message = "Conteúdo deve ter no mínimo 20 caracteres e no máximo 10000")
        String conteudo) {
}
