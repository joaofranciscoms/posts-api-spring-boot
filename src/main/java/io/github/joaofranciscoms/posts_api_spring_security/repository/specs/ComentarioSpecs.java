package io.github.joaofranciscoms.posts_api_spring_security.repository.specs;

import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import org.springframework.data.jpa.domain.Specification;

public class ComentarioSpecs {

    public static Specification<Comentario> conteudoLike(String conteudo){
        return (root, query, cb) -> cb.like(cb.upper(root.get("conteudo")), "%" + conteudo.toUpperCase() + "%");
    }
}
