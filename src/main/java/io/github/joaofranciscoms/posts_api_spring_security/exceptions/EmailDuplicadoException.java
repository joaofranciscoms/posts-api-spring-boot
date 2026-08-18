package io.github.joaofranciscoms.posts_api_spring_security.exceptions;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String message) {
        super(message);
    }
}
