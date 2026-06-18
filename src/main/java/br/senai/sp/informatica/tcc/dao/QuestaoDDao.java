package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.QuestaoD;

@Repository
public class QuestaoDDao implements InterfaceDao<QuestaoD> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(QuestaoD objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(QuestaoD objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		QuestaoD q = manager.find(QuestaoD.class, id);
		manager.remove(q);

	}

	@Override
	public QuestaoD buscar(long id) {
		return manager.find(QuestaoD.class, id);
	}

	@Override
	public List<QuestaoD> getLista() {
		TypedQuery<QuestaoD> query = manager.createQuery("select q from QuestaoD q", QuestaoD.class);
		return query.getResultList();
	}

}
