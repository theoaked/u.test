package br.senai.sp.informatica.tcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Evento;
import br.senai.sp.informatica.tcc.model.Prova;
import br.senai.sp.informatica.tcc.model.Questao;

@Repository
public class EventoDaoJDBC {
	private Connection conexao;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public EventoDaoJDBC(DataSource dataSource) {
		try {
			this.conexao = dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarQuestaoD1(Questao questao) {
		String sql = "UPDATE evento SET nome = ?, area_id = ? WHERE id= ?";
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

	// adiciona enunciado e area na questao dissertativa
	public void alterarEvento1(Evento e) {
		String sql = "UPDATE evento SET status = 1 WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, e.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	// adiciona enunciado e area na questao dissertativa
	public void alterarEvento2(Evento e) {
		String sql = "UPDATE evento SET status = 2 WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, e.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	// incrementa seu cuuuuuuuuuuuuuuuuu
	public void alterarProva(Prova e) {
		String sql = "UPDATE prova SET aplicacoesA = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, e.getAplicacoesA()+1);
			stmt.setLong(2, e.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
