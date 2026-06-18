package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Professor;

@Repository
public class ProfessorDao implements InterfaceDao<Professor> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Professor objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Professor objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Professor p = manager.find(Professor.class, id);
		manager.remove(p);

	}

	@Override
	public Professor buscar(long id) {
		return manager.find(Professor.class, id);
	}

	@Override
	public List<Professor> getLista() {
		TypedQuery<Professor> query = manager.createQuery("select p from Professor p", Professor.class);
		return query.getResultList();
	}
	
	public Professor buscaPorUsuario(long id) {
		TypedQuery<Professor> query = manager.createQuery("select p from Professor p where p.id=" + id, Professor.class);
		return query.getSingleResult();
	}

}
