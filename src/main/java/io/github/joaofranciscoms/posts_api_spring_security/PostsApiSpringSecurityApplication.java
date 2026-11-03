package io.github.joaofranciscoms.posts_api_spring_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PostsApiSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsApiSpringSecurityApplication.class, args);
	}

}
