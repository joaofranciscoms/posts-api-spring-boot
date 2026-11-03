package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CadastroComentarioMapper {

    Comentario toEntity(CadastroComentarioDTO cadastroComentarioDTO);
}
