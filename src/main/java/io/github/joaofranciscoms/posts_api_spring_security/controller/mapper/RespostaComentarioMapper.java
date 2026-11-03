package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RespostaComentarioMapper {

    @Mapping(source = "autor.id", target = "idAutor")
    @Mapping(source = "autor.login", target = "nomeAutor")
    RespostaComentarioDTO toDTO(Comentario comentario);
}
