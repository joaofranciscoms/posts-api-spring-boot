package io.github.joaofranciscoms.posts_api_spring_security.controller;

import io.github.joaofranciscoms.posts_api_spring_security.common.AutoriaUtil;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.AtualizarComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaComentarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.RespostaComentarioMapper;
import io.github.joaofranciscoms.posts_api_spring_security.model.Comentario;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
@Tag(name = "Comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final RespostaComentarioMapper respostaComentarioMapper;

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Busca um comentário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado.")
    })
    public ResponseEntity<Object> buscarComentarioPorId(@PathVariable("id") String id){
        var idComentario = UUID.fromString(id);
        Optional<Comentario> comentarioOptional = comentarioService.findById(idComentario);

        if(comentarioOptional.isPresent()){
            Comentario comentario = comentarioOptional.get();
            RespostaComentarioDTO respostaComentarioDTO = respostaComentarioMapper.toDTO(comentario);
            return ResponseEntity.ok(respostaComentarioDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('AUTOR', 'ADMIN', 'LEITOR')")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "403", description = "Usuário não é o autor do comentário."),
            @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso."),
    })
    @Operation(summary = "Deletar", description = "Deleta um comentário. Somente o autor do comentário ou um ADMIN pode deletá-lo")
    public ResponseEntity<Void> deletarComentario(@PathVariable("id") String id,
                                                  @AuthenticationPrincipal Usuario usuarioLogado){
        var idComentario = UUID.fromString(id);
        Optional<Comentario> comentarioOptional = comentarioService.findById(idComentario);

        if(comentarioOptional.isPresent()){
            Comentario comentario = comentarioOptional.get();
            AutoriaUtil.verificarAutoriaOuAdmin(comentario.getAutor(), usuarioLogado);
            comentarioService.delete(comentario);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('AUTOR')")
    @Operation(summary = "Atualizar", description = "Atualiza um comentário. Somente o autor do comentário ou um ADMIN pode atualizá-lo")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "403", description = "Usuário não é o autor do comentário."),
            @ApiResponse(responseCode = "204", description = "Comentário atualizado com sucesso."),
    })
    public ResponseEntity<Void> atualizarComentario(@PathVariable("id") String id,
                                                    @RequestBody AtualizarComentarioDTO comentarioDTO,
                                                    @AuthenticationPrincipal Usuario usuarioLogado){
        var idComentario = UUID.fromString(id);
        Optional<Comentario> comentarioOptional = comentarioService.findById(idComentario);

        if(comentarioOptional.isPresent()){
            Comentario comentario = comentarioOptional.get();
            AutoriaUtil.verificarAutoriaOuAdmin(comentario.getAutor(), usuarioLogado);

            comentario.setConteudo(comentarioDTO.conteudo());
            comentarioService.update(comentario);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Listar/Paginar", description = "Lista os comentários de forma paginada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado.")
    })
    public ResponseEntity<Page<RespostaComentarioDTO>> pesquisarComentario(
            @RequestParam(value = "conteudo", required = false)
            String conteudo,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina)
    {
        Page<Comentario> comentarioPage = comentarioService.search(conteudo, pagina, tamanhoPagina);
        Page<RespostaComentarioDTO> dto = comentarioPage.map(respostaComentarioMapper::toDTO);

        return ResponseEntity.ok(dto);
    }
}
