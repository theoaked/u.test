package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Administrador;

@Repository
public class AdministradorDao implements InterfaceDao<Administrador> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Administrador objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Administrador objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Administrador a = manager.find(Administrador.class, id);
		manager.remove(a);

	}

	@Override
	public Administrador buscar(long id) {
		return manager.find(Administrador.class, id);
	}

	@Override
	public List<Administrador> getLista() {
		TypedQuery<Administrador> query = manager.createQuery("select a from Administrador a", Administrador.class);
		return query.getResultList();
	}
	
	public Administrador buscaPorUsuario(long id) {
		TypedQuery<Administrador> query = manager.createQuery("select a from Administrador a where a.id=" + id, Administrador.class);
		return query.getSingleResult();
	}

}
