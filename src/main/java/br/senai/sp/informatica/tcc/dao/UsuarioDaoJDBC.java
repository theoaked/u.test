package br.senai.sp.informatica.tcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Aluno;

@Repository
public class UsuarioDaoJDBC {
	private Connection conexao;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public UsuarioDaoJDBC(DataSource dataSource) {
		try {
			this.conexao = dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// adiciona enunciado e area na questao dissertativa
	public void aumentarLevel(Aluno a, double x) {
		String sql = "UPDATE aluno SET level = ? WHERE id= ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			System.out.println("OOOOOOOOOO x:"+x);
			System.out.println("OOOOOOOOOO level anterior: "+a.getLevel());
			stmt.setDouble(1, a.getLevel()+x);
			stmt.setLong(2, a.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
