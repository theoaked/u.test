package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Area;

@Repository
public class AreaDao implements InterfaceDao<Area> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Area objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Area objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Area a = manager.find(Area.class, id);
		manager.remove(a);
	}

	@Override
	public Area buscar(long id) {
		return manager.find(Area.class, id);
	}

	@Override
	public List<Area> getLista() {
		TypedQuery<Area> query = manager.createQuery("select a from Area a order by nome", Area.class);
		return query.getResultList();
	}
	
	public Area getAreaById(long id){
		TypedQuery<Area> query = manager.createQuery("select a from Area a where a.id=" + id, Area.class);
		return query.getSingleResult();
	}

}
