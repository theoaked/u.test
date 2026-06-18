package br.senai.sp.informatica.tcc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Evento;
import br.senai.sp.informatica.tcc.model.Prova;
import br.senai.sp.informatica.tcc.model.Questao;

@Repository
public class EventoDaoJDBC {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public EventoDaoJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarQuestaoD1(Questao questao) {
		String sql = "UPDATE evento SET nome = ?, area_id = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getEnunciado(), questao.getArea().getId(), questao.getId());
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarEvento1(Evento e) {
		String sql = "UPDATE evento SET status = 1 WHERE id= ?";
		jdbcTemplate.update(sql, e.getId());
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarEvento2(Evento e) {
		String sql = "UPDATE evento SET status = 2 WHERE id= ?";
		jdbcTemplate.update(sql, e.getId());
	}

	// incrementa o numero de aplicacoes da prova
	public void alterarProva(Prova e) {
		String sql = "UPDATE prova SET aplicacoesA = ? WHERE id= ?";
		jdbcTemplate.update(sql, e.getAplicacoesA() + 1, e.getId());
	}

}
