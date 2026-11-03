package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.github.joaofranciscoms.posts_api_spring_security.model.RoleUsuario;

public record UsuarioDetalhesDTO(String login, String password, RoleUsuario role) {
}
