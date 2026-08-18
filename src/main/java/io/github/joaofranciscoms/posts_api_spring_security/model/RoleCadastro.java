package io.github.joaofranciscoms.posts_api_spring_security.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.joaofranciscoms.posts_api_spring_security.exceptions.RoleInvalidaException;

import java.util.Arrays;

public enum RoleCadastro {
    AUTOR,
    LEITOR;

    @JsonCreator
    public static RoleCadastro fromString(String valor) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(valor))
                .findFirst()
                .orElseThrow(() -> new RoleInvalidaException("Valor inválido para o campo 'role'! Valores permitidos: AUTOR, LEITOR."));
    }
}
