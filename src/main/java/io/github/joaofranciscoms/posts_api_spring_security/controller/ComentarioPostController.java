package io.github.joaofranciscoms.posts_api_spring_security.controller;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.CadastroComentarioMapper;
import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import io.github.joaofranciscoms.posts_api_spring_security.model.Post;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.service.ComentarioService;
import io.github.joaofranciscoms.posts_api_spring_security.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{idPost}/comentarios")
@RequiredArgsConstructor
@Tag(name = "ComentarioPost")
public class ComentarioPostController implements GenericController{

    private final ComentarioService comentarioService;
    private final PostService postService;
    private final CadastroComentarioMapper cadastroComentarioMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTOR', 'ADMIN', 'LEITOR')")
    @Operation(summary = "Salvar", description = "Salva um novo comentário em um post, em nome do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "404", description = "Post não encontrado."),
            @ApiResponse(responseCode = "400", description = "Erro de validação."),
    })
    public ResponseEntity<Object> salvarComentario(@RequestBody @Valid CadastroComentarioDTO comentarioDTO,
                                                   @PathVariable("idPost") String id,
                                                   @AuthenticationPrincipal Usuario usuarioLogado){
        Post post = postService.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));

        Comentario comentario = cadastroComentarioMapper.toEntity(comentarioDTO);
        comentario.setPost(post);
        comentario.setAutor(usuarioLogado);

        comentarioService.save(comentario);

        URI location = getURI(comentario.getId());
        return ResponseEntity.created(location).build();
    }
}
