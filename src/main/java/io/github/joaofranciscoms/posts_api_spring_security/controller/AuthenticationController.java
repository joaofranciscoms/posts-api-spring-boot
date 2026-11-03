package io.github.joaofranciscoms.posts_api_spring_security.controller;

import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.LoginResponseDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.UsuarioDTO;
import io.github.joaofranciscoms.posts_api_spring_security.controller.dto.UsuarioDetalhesDTO;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.UsuarioRepository;
import io.github.joaofranciscoms.posts_api_spring_security.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o usuário e devolve um token JWT", security = {})
    public ResponseEntity<Object> login(@RequestBody @Valid UsuarioDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar", description = "Registra um novo usuário", security = {})
    public ResponseEntity<Void> register(@RequestBody UsuarioDetalhesDTO dto){
        if(usuarioRepository.findByLogin(dto.login()) != null){
            return ResponseEntity.badRequest().build();
        }
        var encryptedPassword = passwordEncoder.encode(dto.password());
        Usuario usuario = new Usuario(dto.login(), encryptedPassword, dto.role());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
