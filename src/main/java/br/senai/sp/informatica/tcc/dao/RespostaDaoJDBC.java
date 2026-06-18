package br.senai.sp.informatica.tcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Gabarito;
import br.senai.sp.informatica.tcc.model.Resposta;

@Repository
public class RespostaDaoJDBC {
	private Connection conexao;

	@Autowired
	public RespostaDaoJDBC(DataSource dataSource) {
		try {
			this.conexao = dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarResposta1(Resposta r) {
		String sql = "UPDATE resposta SET consideracao = ?, correta = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, r.getConsideracao());
			stmt.setLong(2, r.getCorreta());
			stmt.setLong(3, r.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void alterarGabarito1(Gabarito g) {
		String sql = "UPDATE gabarito SET pontos = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setDouble(1, g.getPontos());
			stmt.setLong(2, g.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void alterarGabarito2(Gabarito g) {
		String sql = "UPDATE gabarito SET nota = ?, corrigida = TRUE WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator('.');
			symbols.setGroupingSeparator(' ');
			df.setDecimalFormatSymbols(symbols);
			double aux = Double.parseDouble(df.format(g.getNota()));
			stmt.setDouble(1, aux);
			stmt.setLong(2, g.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
