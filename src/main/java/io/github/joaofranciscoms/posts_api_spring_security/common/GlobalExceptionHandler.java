package io.github.joaofranciscoms.posts_api_spring_security.common;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException e) {
        return build(HttpStatus.BAD_REQUEST, "Corpo da requisição ausente ou malformado", Map.of());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException e) {
        return build(HttpStatus.UNAUTHORIZED, "Login ou senha inválidos", Map.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException e) {
        return build(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso", Map.of());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage(), Map.of());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException e) {
        return build(HttpStatus.NOT_FOUND, "Recurso não encontrado", Map.of());
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String mensagem, Map<String, Object> extras) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", mensagem);
        body.putAll(extras);
        return ResponseEntity.status(status).body(body);
    }
}
