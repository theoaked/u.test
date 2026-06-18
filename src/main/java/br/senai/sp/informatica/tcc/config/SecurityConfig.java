package br.senai.sp.informatica.tcc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracao de seguranca da aplicacao modernizada.
 *
 * <p>Decisoes de migracao:
 * <ul>
 *   <li><b>BCrypt</b> substitui o armazenamento de senha em texto puro. O encoder
 *       e exposto como bean e usado no login ({@code UsuarioController}) e nos
 *       cadastros (Aluno/Professor/Administrador).</li>
 *   <li>O controle de acesso fino continua sendo feito pelas verificacoes de
 *       sessao ja existentes nos controllers ({@code session.getAttribute("admLogado")}
 *       etc.), que sao a logica original do sistema. Por isso a cadeia de filtros
 *       e permissiva: aplicar regras de URL por papel exigiria reestruturar todas
 *       as rotas (mudando os links/visual), o que contraria o objetivo de preservar
 *       o comportamento. O Spring Security fica configurado e pronto para endurecer
 *       o acesso por papel quando desejado.</li>
 *   <li>CSRF desabilitado: os formularios originais (migrados para Thymeleaf) nao
 *       possuem token CSRF; habilita-lo exigiria alterar todas as telas.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			// Login proprio da aplicacao (pagina /login + endpoint /logar); desabilita
			// o form login e o basic padrao do Spring Security.
			.formLogin(form -> form.disable())
			.httpBasic(basic -> basic.disable())
			.logout(logout -> logout.disable());
		return http.build();
	}
}
