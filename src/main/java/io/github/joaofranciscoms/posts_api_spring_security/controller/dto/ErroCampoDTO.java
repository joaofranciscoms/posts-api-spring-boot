package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Campo de Erro")
public record ErroCampoDTO(String campo, String erro) {
}
