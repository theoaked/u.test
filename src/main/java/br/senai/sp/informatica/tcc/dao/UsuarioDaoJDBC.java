package br.senai.sp.informatica.tcc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Aluno;

@Repository
public class UsuarioDaoJDBC {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UsuarioDaoJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// aumenta o level (pontuacao acumulada) do aluno
	public void aumentarLevel(Aluno a, double x) {
		String sql = "UPDATE aluno SET level = ? WHERE id= ?";
		jdbcTemplate.update(sql, a.getLevel() + x, a.getId());
	}

}
