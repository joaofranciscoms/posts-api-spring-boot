package io.github.joaofranciscoms.posts_api_spring_security.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Resposta de Erro")
public record ErroRespostaDTO(int status, String mensagem, List<ErroCampoDTO> erros) {
}
