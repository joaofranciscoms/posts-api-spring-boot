package io.github.joaofranciscoms.posts_api_spring_security.config;


import io.github.joaofranciscoms.posts_api_spring_security.model.Role;
import io.github.joaofranciscoms.posts_api_spring_security.model.Usuario;
import io.github.joaofranciscoms.posts_api_spring_security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {

    @Value("${app.default-admin.email}")
    private String adminEmail;

    @Value("${app.default-admin.password}")
    private String adminSenha;

    @Bean
    public CommandLineRunner seedAdmin(UsuarioRepository usuarioRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {

            if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {

                Usuario admin = new Usuario();
                admin.setLogin("admin");
                admin.setEmail(adminEmail);

                admin.setPassword(passwordEncoder.encode(adminSenha));

                admin.setRole(Role.ADMIN);

                // 3. Salva no banco de dados
                usuarioRepository.save(admin);

                System.out.println("Database Seeding: Usuário ADMIN criado com sucesso!");
            } else {
                System.out.println("Database Seeding: Usuário ADMIN já existe no banco.");
            }
        };
    }
}
