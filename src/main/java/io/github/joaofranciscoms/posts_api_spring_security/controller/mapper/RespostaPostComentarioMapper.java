package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaPostComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RespostaPostComentarioMapper {

    @Mapping(source = "autor.login", target = "autor")
    RespostaPostComentarioDTO toDTO(Comentario comentario);
}
