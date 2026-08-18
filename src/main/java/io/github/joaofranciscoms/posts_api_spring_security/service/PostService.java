package io.github.joaofranciscoms.posts_api_spring_security.service;

import io.github.joaofranciscoms.posts_api_spring_security.model.Post;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.PostRepository;
import io.github.joaofranciscoms.posts_api_spring_security.repository.specs.PostSpecs;
import io.github.joaofranciscoms.posts_api_spring_security.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final SecurityService service;

    public void save(Post post){
        Usuario usuario = service.usuarioLogado();
        post.setAutor(usuario);
        repository.save(post);
    }

    public Optional<Post> findById(UUID id){
        return repository.findById(id);
    }

    public void delete(Post post){
        repository.delete(post);
    }

    public void update(Post post){
        repository.save(post);
    }

    public Page<Post> search(String titulo, String conteudo, Integer pagina, Integer tamanhoPagina){

        Specification<Post> postSpecification = Specification.where((root, query, cb) -> cb.conjunction());

        if(titulo != null){
            postSpecification = postSpecification.and(PostSpecs.tituloLike(titulo));
        }

        if(conteudo != null){
            postSpecification = postSpecification.and(PostSpecs.conteudoLike(conteudo));
        }

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(postSpecification, pageable);
    }
}
