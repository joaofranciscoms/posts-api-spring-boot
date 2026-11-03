package io.github.joaofranciscoms.posts_api_spring_security.controller;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.AtualizarPostDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroPostDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaPostDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.CadastroPostMapper;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.RespostaPostMapper;
import io.github.joaofranciscoms.posts_api_spring_security.model.Post;
import io.github.joaofranciscoms.posts_api_spring_security.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts")
public class PostController implements GenericController {

    private final PostService service;
    private final CadastroPostMapper cadastroPostMapper;
    private final RespostaPostMapper respostaPostMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTOR', 'ADMIN')")
    @Operation(summary = "Salvar", description = "Salva um novo post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "400", description = "Erro de validação."),
    })
    public ResponseEntity<Object> salvarPost(@RequestBody @Valid CadastroPostDTO postDTO){
        Post post = cadastroPostMapper.toEntity(postDTO);
        service.save(post);
        URI location = getURI(post.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Busca um post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Post não encontrado.")
    })
    public ResponseEntity<Object> buscarPostPorId(@PathVariable("id") String id){
        var idPost = UUID.fromString(id);
        Optional<Post> postOptional = service.findById(idPost);

        if(postOptional.isPresent()){
            Post post = postOptional.get();
            RespostaPostDTO respostaPostDTO = respostaPostMapper.toDTO(post);
            return ResponseEntity.ok(respostaPostDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('AUTOR', 'ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um autor")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Post não encontrado."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "204", description = "Post deletado com sucesso."),
    })
    public ResponseEntity<Void> deletarPost(@PathVariable("id") String id){
        var idPost = UUID.fromString(id);
        Optional<Post> postOptional = service.findById(idPost);

        if(postOptional.isPresent()){
            Post post = postOptional.get();
            service.delete(post);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTOR')")
    @Operation(summary = "Atualizar", description = "Atualiza um post")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Post não encontrado."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "204", description = "Post atualizado com sucesso."),
    })
    public ResponseEntity<Void> atualizarPost(@PathVariable("id") String id, @RequestBody AtualizarPostDTO postDTO){
        var idPost = UUID.fromString(id);
        Optional<Post> postOptional = service.findById(idPost);

        if(postOptional.isPresent()){
            Post post = postOptional.get();

            post.setTitulo(postDTO.titulo());
            post.setConteudo(postDTO.conteudo());

            service.update(post);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Listar/Paginar", description = "Lista os posts de forma paginada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Post não encontrado.")
    })
    public ResponseEntity<Page<RespostaPostDTO>> pesquisarPost(
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "conteudo", required = false)
            String conteudo,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina)
    {

        Page<Post> postPage= service.search(titulo, conteudo, pagina, tamanhoPagina);
        Page<RespostaPostDTO> dto = postPage.map(respostaPostMapper::toDTO);

        return ResponseEntity.ok(dto);
    }
}
