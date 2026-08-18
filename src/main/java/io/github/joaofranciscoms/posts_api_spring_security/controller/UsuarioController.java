package io.github.joaofranciscoms.posts_api_spring_security.controller;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroUsuarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.RespostaUsuarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.CadastroUsuarioMapper;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.RespostaUsuarioMapper;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios")
public class UsuarioController implements GenericController{

    private final UsuarioService service;
    private final CadastroUsuarioMapper cadastroUsuarioMapper;
    private final RespostaUsuarioMapper respostaUsuarioMapper;

    @PostMapping()
    @Operation(summary = "Salvar", description = "Cadastra um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Usuario já cadastrado com as credenciais passadas.")
    })
    public ResponseEntity<Void> salvarUsuario(@RequestBody @Valid CadastroUsuarioDTO usuarioDTO){
        Usuario usuario = cadastroUsuarioMapper.toEntity(usuarioDTO);
        service.save(usuario);
        URI location = getURI(usuario.getId());
        return ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Busca um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado.")
    })
    public ResponseEntity<Object> buscarUsuarioPorId(@PathVariable("id") String id){
        var idUsuario = UUID.fromString(id);
        Optional<Usuario> usuarioOptional = service.findById(idUsuario);

        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            RespostaUsuarioDTO respostaUsuarioDTO = respostaUsuarioMapper.toDTO(usuario);
            return  ResponseEntity.ok(respostaUsuarioDTO);
        }

        return ResponseEntity.notFound().build();
    }
}
