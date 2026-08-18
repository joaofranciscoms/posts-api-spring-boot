package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CadastroClientDTO(
        @NotBlank(message = "Campo obrigatório!")
        String clientId,
        @NotBlank(message = "Campo obrigatório!")
        String clientSecret,
        @NotBlank(message = "Campo obrigatório!")
        String redirectURI,
        @NotNull(message = "Campo obrigatório!")
        List<String> scopes) {
}
