package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.QuestaoA;

@Repository
public class QuestaoADao implements InterfaceDao<QuestaoA> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(QuestaoA objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(QuestaoA objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		QuestaoA q = manager.find(QuestaoA.class, id);
		manager.remove(q);

	}

	@Override
	public QuestaoA buscar(long id) {
		return manager.find(QuestaoA.class, id);
	}

	@Override
	public List<QuestaoA> getLista() {
		TypedQuery<QuestaoA> query = manager.createQuery("select q from QuestaoA q", QuestaoA.class);
		return query.getResultList();
	}

}
