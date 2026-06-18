package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Prova;

@Repository
public class ProvaDao implements InterfaceDao<Prova> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Prova objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Prova objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Prova p = manager.find(Prova.class, id);
		manager.remove(p);

	}

	@Override
	public Prova buscar(long id) {
		return manager.find(Prova.class, id);
	}

	@Override
	public List<Prova> getLista() {
		TypedQuery<Prova> query = manager.createQuery("select p from Prova p", Prova.class);
		return query.getResultList();
	}
	
	public List<Prova> getListaProf(long id) {
		TypedQuery<Prova> query = manager.createQuery("select p from Prova p where p.professor.id=" + id, Prova.class);
		return query.getResultList();
	}
	
	public Prova getProvaById(long id) {
		TypedQuery<Prova> query = manager.createQuery("select p from Prova p where p.id=" + id, Prova.class);
		return query.getSingleResult();
	}

}
