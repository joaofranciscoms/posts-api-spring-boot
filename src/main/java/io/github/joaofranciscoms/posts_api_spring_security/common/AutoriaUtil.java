package io.github.joaofranciscoms.posts_api_spring_security.common;

import io.github.joaofranciscoms.posts_api_spring_security.model.RoleUsuario;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import org.springframework.security.access.AccessDeniedException;

public final class AutoriaUtil {

    private AutoriaUtil() {
    }
    
    public static void verificarAutoriaOuAdmin(Usuario autorDoRecurso, Usuario usuarioLogado) {
        if (usuarioLogado.getRole() == RoleUsuario.ADMIN) {
            return;
        }
        if (autorDoRecurso != null && autorDoRecurso.getId().equals(usuarioLogado.getId())) {
            return;
        }
        throw new AccessDeniedException("Você não é o autor deste recurso");
    }
}