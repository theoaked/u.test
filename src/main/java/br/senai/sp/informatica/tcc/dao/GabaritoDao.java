package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Gabarito;

@Repository
public class GabaritoDao implements InterfaceDao<Gabarito> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Gabarito objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Gabarito objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Gabarito p = manager.find(Gabarito.class, id);
		manager.remove(p);

	}

	@Override
	public Gabarito buscar(long id) {
		return manager.find(Gabarito.class, id);
	}

	@Override
	public List<Gabarito> getLista() {
		TypedQuery<Gabarito> query = manager.createQuery("select g from Gabarito g", Gabarito.class);
		return query.getResultList();
	}
	
	public List<Gabarito> getListaByEvento(long id) {
		TypedQuery<Gabarito> query = manager.createQuery("select g from Gabarito g where g.evento.id=" + id, Gabarito.class);		
		return query.getResultList();
	}
	
	public Gabarito getListaById(long id) {
		TypedQuery<Gabarito> query = manager.createQuery("select g from Gabarito g where g.id=" + id, Gabarito.class);	
		return query.getSingleResult();
	}
	
	public List<Gabarito> getListaByAluno(long id) {
		TypedQuery<Gabarito> query = manager.createQuery("select g from Gabarito g where g.aluno.id= :id and g.corrigida = TRUE", Gabarito.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	

}
