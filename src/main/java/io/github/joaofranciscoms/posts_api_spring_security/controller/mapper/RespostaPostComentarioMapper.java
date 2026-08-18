package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaPostComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface RespostaPostComentarioMapper {

    @Mapping(source = "autor.login", target = "autor")
    RespostaPostComentarioDTO toDTO(Comentario comentario);
}
