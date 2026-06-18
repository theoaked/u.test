package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Aluno;

@Repository
public class AlunoDao implements InterfaceDao<Aluno> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Aluno objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Aluno objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Aluno a = manager.find(Aluno.class, id);
		manager.remove(a);

	}

	@Override
	public Aluno buscar(long id) {
		return manager.find(Aluno.class, id);
	}

	@Override
	public List<Aluno> getLista() {
		TypedQuery<Aluno> query = manager.createQuery("select a from Aluno a", Aluno.class);
		return query.getResultList();
	}
	
	public List<Aluno> getListaByProf(long id) {
		TypedQuery<Aluno> query = manager.createQuery("select a from Aluno a where a.instituicao.id="+ id, Aluno.class);
		return query.getResultList();
	}
	
	public Aluno getAlunoById(long id) {
		TypedQuery<Aluno> query = manager.createQuery("select a from Aluno a where a.id="+ id, Aluno.class);
		return query.getSingleResult();
	}
	
	public List<Aluno> getAlunoById2(long id) {
		TypedQuery<Aluno> query = manager.createQuery("select a from Aluno a where a.id="+ id, Aluno.class);
		return query.getResultList();
	}
	
	public Aluno logar(String login, String senha, int tipo) {
		TypedQuery<Aluno> query = manager.createQuery(
				"select a from Aluno a where a.login = :login and a.senha = :senha and a.tipoUsuario = :tipoUsuario",
				Aluno.class);
		query.setParameter("login", login);
		query.setParameter("senha", senha);
		query.setParameter("tipoUsuario", tipo);
		if (query.getResultList().size() != 0) {
			return query.getResultList().get(0);
		} else {
			return null;
		}
	}

}
