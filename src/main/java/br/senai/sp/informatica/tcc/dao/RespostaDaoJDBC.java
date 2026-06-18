package br.senai.sp.informatica.tcc.dao;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Gabarito;
import br.senai.sp.informatica.tcc.model.Resposta;

@Repository
public class RespostaDaoJDBC {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public RespostaDaoJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// adiciona consideracao e correcao na resposta dissertativa
	public void alterarResposta1(Resposta r) {
		String sql = "UPDATE resposta SET consideracao = ?, correta = ? WHERE id= ?";
		jdbcTemplate.update(sql, r.getConsideracao(), (long) r.getCorreta(), r.getId());
	}

	public void alterarGabarito1(Gabarito g) {
		String sql = "UPDATE gabarito SET pontos = ? WHERE id= ?";
		jdbcTemplate.update(sql, g.getPontos(), g.getId());
	}

	public void alterarGabarito2(Gabarito g) {
		String sql = "UPDATE gabarito SET nota = ?, corrigida = TRUE WHERE id= ?";
		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);
		double aux = Double.parseDouble(df.format(g.getNota()));
		jdbcTemplate.update(sql, aux, g.getId());
	}

}
