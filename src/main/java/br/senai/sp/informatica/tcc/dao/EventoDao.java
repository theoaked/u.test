package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Evento;

@Repository
public class EventoDao implements InterfaceDao<Evento> {
	@PersistenceContext
	private EntityManager manager;


	@Override
	public void inserir(Evento objeto) {
		manager.persist(objeto);
	}


	@Override
	public void alterar(Evento objeto) {
		manager.merge(objeto);
	}


	@Override
	public void excluir(long id) {
		Evento a = manager.find(Evento.class, id);
		manager.remove(a);
	}

	@Override
	public Evento buscar(long id) {
		return manager.find(Evento.class, id);
	}

	@Override
	public List<Evento> getLista() {
		TypedQuery<Evento> query = manager.createQuery("select e from Evento e", Evento.class);
		return query.getResultList();
	}
	
	public Evento getEventoById(long id) {
		TypedQuery<Evento> query = manager.createQuery("select e from Evento e where e.id=" + id, Evento.class);
		return query.getSingleResult();
	}
	
	public List<Evento> getListaDeEventosPorId(long id){
		TypedQuery<Evento> query = manager.createQuery("select e from Evento e join e.alunosParticipantes a where a.id = :id and e.status = 0 and data = current_date", Evento.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Evento> getListaProf(long id){
		TypedQuery<Evento> query = manager.createQuery("select e from Evento e where e.professor.id = :id order by data", Evento.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Evento> getListaDeEventosPorId2(long id){
		TypedQuery<Evento> query = manager.createQuery("select e from Evento e join e.alunosParticipantes a where a.id = :id and e.status = 2", Evento.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Evento> getListaDeEventosPorId1(long id){
		TypedQuery<Evento> query = manager.createQuery("select e from Evento e join e.alunosParticipantes a where a.id = :id and e.status = 1", Evento.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

}
