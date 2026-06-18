package br.senai.sp.informatica.tcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Questao;

@Repository
public class QuestaoDaoJDBC {
	private Connection conexao;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public QuestaoDaoJDBC(DataSource dataSource) {
		try {
			this.conexao = dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarQuestaoD1(Questao questao) {
		String sql = "UPDATE questao SET enunciado = ?, area_id = ?, ativa = TRUE WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, questao.getEnunciado());
			stmt.setLong(2, questao.getArea().getId());
			stmt.setLong(3, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// coloca a questao na geladeira
	public void colocarNaGeladeira(Questao questao) {
		String sql = "UPDATE questao SET ativa = FALSE, dataInativa = current_date WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// tira a questao da geladeira
	public void tirarDaGeladeira(Questao questao) {
		String sql = "UPDATE questao SET ativa = TRUE WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// aumenta a dificuldade da questao
	public void aumentaDificuldade(Questao questao) {
		String sql = "UPDATE questao SET dificuldade = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, questao.getDificuldade() + 1);
			stmt.setLong(2, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// diminui a dificuldade da questao
	public void diminuiDificuldade(Questao questao) {
		String sql = "UPDATE questao SET dificuldade = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, questao.getDificuldade() - 1);
			stmt.setLong(2, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// + aplicacao
	public void maisAplicacao(Questao questao) {
		String sql = "UPDATE questao SET aplicacoes = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, questao.getAplicacoes() + 1);
			stmt.setLong(2, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// + utilizacao
	public void maisUtilizacao(Questao questao) {
		String sql = "UPDATE questao SET utilizacoes = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, questao.getUtilizacoes() + 1);
			stmt.setLong(2, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// + acerto
	public void maisAcerto(Questao questao) {
		String sql = "UPDATE questao SET acertos = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, questao.getAcertos() + 1);
			stmt.setLong(2, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// + erro
	public void maisErro(Questao questao) {
		String sql = "UPDATE questao SET erros = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, questao.getErros() + 1);
			stmt.setLong(2, questao.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
