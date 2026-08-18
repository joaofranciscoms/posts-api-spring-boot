package io.github.joaofranciscoms.posts_api_spring_security.service;

import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.ComentarioRepository;
import io.github.joaofranciscoms.posts_api_spring_security.repository.specs.ComentarioSpecs;
import io.github.joaofranciscoms.posts_api_spring_security.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository repository;
    private final SecurityService service;

    public void save(Comentario comentario){
        Usuario usuario = service.usuarioLogado();
        comentario.setAutor(usuario);
        repository.save(comentario);
    }

    public Optional<Comentario> findById(UUID id){
        return repository.findById(id);
    }

    public void delete(Comentario comentario){
        repository.delete(comentario);
    }

    public void update(Comentario comentario){
        repository.save(comentario);
    }

    public Page<Comentario> search(String conteudo, Integer pagina, Integer tamanhoPagina){

        Specification<Comentario> comentarioSpecification  = Specification.where((root, query, cb) -> cb.conjunction());

        if(conteudo != null){
            comentarioSpecification = comentarioSpecification.and(ComentarioSpecs.conteudoLike(conteudo));
        }

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(comentarioSpecification, pageable);
    }
}
