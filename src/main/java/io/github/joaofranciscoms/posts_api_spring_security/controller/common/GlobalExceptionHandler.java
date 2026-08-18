package io.github.joaofranciscoms.posts_api_spring_security.controller.common;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.ErroCampoDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.ErroRespostaDTO;
import io.github.joaofranciscoms.posts_api_spring_security.exceptions.EmailDuplicadoException;
import io.github.joaofranciscoms.posts_api_spring_security.exceptions.RoleInvalidaException;
import io.github.joaofranciscoms.posts_api_spring_security.exceptions.UsernameDuplicadoException;
import io.github.joaofranciscoms.posts_api_spring_security.exceptions.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){

        List<FieldError> fieldErrorsList = e.getFieldErrors();
        List<ErroCampoDTO> erroCampoDTOList = fieldErrorsList.stream().map(erroDTO -> new ErroCampoDTO(
                erroDTO.getField(),
                erroDTO.getDefaultMessage())).collect(Collectors.toList());

        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), "Erro de validação", erroCampoDTOList);
    }

    @ExceptionHandler(UsernameDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroRespostaDTO handlerUsernameDuplicadoException(UsernameDuplicadoException e){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroRespostaDTO handlerEmailDuplicadoException(EmailDuplicadoException e){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroRespostaDTO handlerUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e){
        return new ErroRespostaDTO(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(RoleInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handlerRoleInvalidaException(RoleInvalidaException e){
        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroRespostaDTO handlerRuntimeException(RuntimeException e){
        return new ErroRespostaDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro desconhecido! Fale com o suporte.", List.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        Throwable causa = e;

        while (causa != null) {
            if (causa instanceof RoleInvalidaException roleInvalidaException) {
                return new ErroRespostaDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        roleInvalidaException.getMessage(),
                        List.of()
                );
            }

            causa = causa.getCause();
        }

        return new ErroRespostaDTO(
                HttpStatus.BAD_REQUEST.value(),
                "JSON inválido!",
                List.of()
        );
    }
}
