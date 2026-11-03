package io.github.joaofranciscoms.posts_api_spring_security.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {
    default URI getURI(UUID id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
