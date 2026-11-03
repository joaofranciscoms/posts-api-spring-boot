package io.github.joaofranciscoms.posts_api_spring_security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${app.jwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("postsapi")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        } catch(JWTCreationException e){
            throw new RuntimeException("Error while generating the token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("postsapi").build().verify(token).getSubject();
        } catch(JWTVerificationException e){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
