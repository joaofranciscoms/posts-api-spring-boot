package io.github.joaofranciscoms.posts_api_spring_security.exceptions;

public class RoleInvalidaException extends RuntimeException {
    public RoleInvalidaException(String message) {
        super(message);
    }
}
