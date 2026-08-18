package io.github.joaofranciscoms.posts_api_spring_security.controller;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.CadastroClientDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.mapper.CadastroClientMapper;
import io.github.joaofranciscoms.posts_api_spring_security.model.Client;
import io.github.joaofranciscoms.posts_api_spring_security.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
public class ClientController {

    private final ClientService service;
    private final CadastroClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar", description = "Cadastrar um novo client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token de acesso ausente ou inválido."),
            @ApiResponse(responseCode = "400", description = "Erro de validação."),
    })
    public void salvarClient(@RequestBody CadastroClientDTO clientDTO){
        Client client = mapper.toEntity(clientDTO);
        service.save(client);
    }
}
