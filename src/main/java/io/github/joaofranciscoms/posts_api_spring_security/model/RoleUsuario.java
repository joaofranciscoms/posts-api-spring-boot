package io.github.joaofranciscoms.posts_api_spring_security.model;

import lombok.Getter;

@Getter
public enum RoleUsuario {
    ADMIN("admin"),
    USER("user");

    private String role;

    RoleUsuario(String role){
        this.role = role;
    }
}
