package io.github.joaofranciscoms.posts_api_spring_security.controller.mapper;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaPostDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RespostaPostComentarioMapper.class)
public interface RespostaPostMapper {

    @Mapping(expression = "java( post.getAutor().getLogin() )", target = "autor")
    RespostaPostDTO toDTO(Post post);
}
