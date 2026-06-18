package br.senai.sp.informatica.tcc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * Smoke test: sobe o contexto completo (Security, JPA, DataSeeder) num H2 em
 * memoria e renderiza paginas-chave, exercitando os fragments Thymeleaf
 * compartilhados (cabecalho/rodape) usados por todas as telas.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmokeTest {

	@Autowired
	private TestRestTemplate rest;

	@Test
	void contextLoads() {
	}

	@Test
	void paginaInicialRenderiza() {
		ResponseEntity<String> resp = rest.getForEntity("/index", String.class);
		assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(resp.getBody()).contains("u.test");
	}

	@Test
	void paginaLoginRenderiza() {
		ResponseEntity<String> resp = rest.getForEntity("/login", String.class);
		assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(resp.getBody()).contains("Cadastre-se");
	}

	@Test
	void listaAreaRenderizaComSeed() {
		// /lista_area exige admin logado; sem sessao retorna a tela "sem_acesso".
		ResponseEntity<String> resp = rest.getForEntity("/lista_area", String.class);
		assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(resp.getBody()).contains("SEM ACESSO");
	}
}
