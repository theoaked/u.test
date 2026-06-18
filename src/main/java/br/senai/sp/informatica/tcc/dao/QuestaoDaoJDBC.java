package br.senai.sp.informatica.tcc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Questao;

@Repository
public class QuestaoDaoJDBC {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public QuestaoDaoJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarQuestaoD1(Questao questao) {
		String sql = "UPDATE questao SET enunciado = ?, area_id = ?, ativa = TRUE WHERE id= ?";
		jdbcTemplate.update(sql, questao.getEnunciado(), questao.getArea().getId(), questao.getId());
	}

	// coloca a questao na geladeira
	public void colocarNaGeladeira(Questao questao) {
		String sql = "UPDATE questao SET ativa = FALSE, dataInativa = current_date WHERE id= ?";
		jdbcTemplate.update(sql, questao.getId());
	}

	// tira a questao da geladeira
	public void tirarDaGeladeira(Questao questao) {
		String sql = "UPDATE questao SET ativa = TRUE WHERE id= ?";
		jdbcTemplate.update(sql, questao.getId());
	}

	// aumenta a dificuldade da questao
	public void aumentaDificuldade(Questao questao) {
		String sql = "UPDATE questao SET dificuldade = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getDificuldade() + 1, questao.getId());
	}

	// diminui a dificuldade da questao
	public void diminuiDificuldade(Questao questao) {
		String sql = "UPDATE questao SET dificuldade = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getDificuldade() - 1, questao.getId());
	}

	// + aplicacao
	public void maisAplicacao(Questao questao) {
		String sql = "UPDATE questao SET aplicacoes = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getAplicacoes() + 1, questao.getId());
	}

	// + utilizacao
	public void maisUtilizacao(Questao questao) {
		String sql = "UPDATE questao SET utilizacoes = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getUtilizacoes() + 1, questao.getId());
	}

	// + acerto
	public void maisAcerto(Questao questao) {
		String sql = "UPDATE questao SET acertos = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getAcertos() + 1, questao.getId());
	}

	// + erro
	public void maisErro(Questao questao) {
		String sql = "UPDATE questao SET erros = ? WHERE id= ?";
		jdbcTemplate.update(sql, questao.getErros() + 1, questao.getId());
	}

}
