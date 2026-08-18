package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "Resposta Usuario")
public record RespostaUsuarioDTO(
        UUID id,
        String login,
        String email,
        List<String> roles) {
}
