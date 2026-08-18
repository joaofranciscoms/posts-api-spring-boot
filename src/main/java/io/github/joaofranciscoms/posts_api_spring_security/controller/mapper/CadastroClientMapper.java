package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroClientDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CadastroClientMapper {

    Client toEntity(CadastroClientDTO cadastroClientDTO);
}
