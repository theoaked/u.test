package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Instituicao;

@Repository
public class InstituicaoDao implements InterfaceDao<Instituicao> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Instituicao objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Instituicao objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Instituicao i = manager.find(Instituicao.class, id);
		manager.remove(i);
	}

	@Override
	public Instituicao buscar(long id) {
		return manager.find(Instituicao.class, id);
	}

	@Override
	public List<Instituicao> getLista() {
		TypedQuery<Instituicao> query = manager.createQuery("select i from Instituicao i order by nome", Instituicao.class);
		return query.getResultList();
	}
	
	public List<Instituicao> getListaAvaliativa() {
		TypedQuery<Instituicao> query = manager.createQuery("select i from Instituicao i where tipo=true order by nome", Instituicao.class);
		return query.getResultList();
	}
	
	public List<Instituicao> getListaEscola() {
		TypedQuery<Instituicao> query = manager.createQuery("select i from Instituicao i where tipo=false order by nome", Instituicao.class);
		return query.getResultList();
	}

}
