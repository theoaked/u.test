package br.senai.sp.informatica.tcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// A autenticacao e feita pelo login proprio da aplicacao (UsuarioController + BCrypt),
// portanto excluimos o UserDetailsService padrao do Spring Boot (e o "generated password").
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class UTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UTestApplication.class, args);
	}

}
