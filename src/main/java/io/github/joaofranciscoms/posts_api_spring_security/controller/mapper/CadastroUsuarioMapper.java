package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroUsuarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CadastroUsuarioMapper {

    Usuario toEntity(CadastroUsuarioDTO cadastroUsuarioDTO);
}
