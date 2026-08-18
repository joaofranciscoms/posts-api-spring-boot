package io.github.joaofranciscoms.posts_api_spring_security.exceptions;

public class UsernameDuplicadoException extends RuntimeException {
    public UsernameDuplicadoException(String message) {
        super(message);
    }
}
