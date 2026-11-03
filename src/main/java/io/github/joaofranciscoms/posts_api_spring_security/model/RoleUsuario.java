package io.github.joaofranciscoms.posts_api_spring_security.model;

import lombok.Getter;

@Getter
public enum RoleUsuario {
    ADMIN("admin"),
    AUTOR("autor"),
    LEITOR("leitor");

    private String role;

    RoleUsuario(String role){
        this.role = role;
    }
}
