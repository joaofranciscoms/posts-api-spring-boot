package io.github.joaofranciscoms.posts_api_spring_security.repository.specs;

import io.github.joaofranciscoms.posts_api_spring_security.model.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecs {
    public static Specification<Post> tituloLike(String titulo){
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Post> conteudoLike(String conteudo){
        return (root, query, cb) -> cb.like(cb.upper(root.get("conteudo")), "%" + conteudo.toUpperCase() + "%");
    }
}
