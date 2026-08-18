package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroPostDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CadastroPostMapper {

    Post toEntity(CadastroPostDTO cadastroPostDTO);
}
