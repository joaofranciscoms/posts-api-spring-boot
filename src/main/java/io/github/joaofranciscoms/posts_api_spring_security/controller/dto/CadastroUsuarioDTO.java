package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.github.joaofranciscoms.posts_api_spring_security.model.RoleCadastro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "Cadastrar Usuario")
public record CadastroUsuarioDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 3, max = 30, message = "Username deve ter entre 3 e 30 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username não pode conter espaços ou caracteres especiais")
        String login,
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String password,
        @NotBlank(message = "Campo obrigatório!")
        @Email(message = "Email inválido!")
        String email,
        @NotNull(message = "Campo obrigatório!")
        RoleCadastro role) {
}
